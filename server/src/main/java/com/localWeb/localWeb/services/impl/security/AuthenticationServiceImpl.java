package com.localWeb.localWeb.services.impl.security;

import com.localWeb.localWeb.enums.TokenType;
import com.localWeb.localWeb.exceptions.token.InvalidTokenException;
import com.localWeb.localWeb.exceptions.user.UserLoginException;
import com.localWeb.localWeb.models.dto.auth.AuthenticationRequest;
import com.localWeb.localWeb.models.dto.auth.AuthenticationResponse;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.auth.RegisterRequest;
import com.localWeb.localWeb.models.dto.request.CompleteOAuthRequest;
import com.localWeb.localWeb.models.entity.Token;
import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.models.entity.VerificationToken;
import com.localWeb.localWeb.repositories.UserRepository;
import com.localWeb.localWeb.repositories.VerificationTokenRepository;
import com.localWeb.localWeb.services.AuthenticationService;
import com.localWeb.localWeb.services.JwtService;
import com.localWeb.localWeb.services.TokenService;
import com.localWeb.localWeb.services.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user based on the provided registration request.
     */
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = userService.createUser(request);

        try {
            userService.setAddressesForUser(request, user);
            userService.setPhoneNumberForUser(request, user);
        } catch (Exception e) {
            user.setDeletedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        return tokenService.generateAuthResponse(user);
    }

    /**
     * Completes the OAuth2 authentication process for the provided user and generates an authentication response.
     * It updates the user's information with the data obtained from the OAuth2 provider.
     */
    @Override
    public AuthenticationResponse completeOAuth2(CompleteOAuthRequest request, PublicUserDTO currentLoggedUser) {
        User updatedUser = userService.updateOAuth2UserWithFullData(request, currentLoggedUser.getId());

        userService.setAddressesForUser(request, updatedUser);
        userService.setPhoneNumberForUser(request, updatedUser);

        return tokenService.generateAuthResponse(updatedUser);
    }

    // Login with correct email and password
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException exception) {
            throw new UserLoginException(messageSource);
        }

        User user = userService.findByEmail(request.getEmail());
        tokenService.revokeAllUserTokens(user);

        return tokenService.generateAuthResponse(user);
    }

    /**
     * Generates a new access token and updates the refresh token based on the provided refresh token.
     * If the refresh token is missing or invalid, it throws an InvalidTokenException.
     * If the refresh token is valid, it generates a new access token, revokes all existing user tokens,
     * and updates the refresh token to the provided one.
     */
    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new InvalidTokenException(messageSource);
        }

        String userEmail;

        try {
            userEmail = jwtService.extractUsername(refreshToken);
        } catch (JwtException exception) {
            throw new InvalidTokenException(messageSource);
        }

        if (userEmail == null) {
            throw new InvalidTokenException(messageSource);
        }

        // Make sure token is a refresh token not access token
        Token token = tokenService.findByToken(refreshToken);
        if (token != null && token.tokenType != TokenType.REFRESH) {
            throw new InvalidTokenException(messageSource);
        }

        User user = userService.findByEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            tokenService.revokeToken(token);
            throw new InvalidTokenException(messageSource);
        }

        String accessToken = jwtService.generateToken(user);

        tokenService.revokeAllUserTokens(user);
        tokenService.saveToken(user, accessToken, TokenType.ACCESS);
        tokenService.saveToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Retrieves user information based on the provided JWT token.
     * If the token is invalid or missing, it throws an InvalidTokenException.
     * If the token is valid, it retrieves the user's access and refresh tokens, updates the refresh token if necessary,
     * and returns an authentication response containing the user's information and tokens.
     */
    @Override
    public AuthenticationResponse me(String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            throw new InvalidTokenException(messageSource);
        }

        Token accessToken = tokenService.findByToken(jwtToken);

        if (accessToken == null) {
            throw new InvalidTokenException(messageSource);
        }

        User user = accessToken.getUser();

        boolean isTokenValid;

        try {
            isTokenValid = jwtService.isTokenValid(accessToken.getToken(), user);
        } catch (JwtException jwtException) {
            isTokenValid = false;
        }

        if (!isTokenValid) {
            tokenService.revokeAllUserTokens(user);
            throw new InvalidTokenException(messageSource);
        }

        List<Token> tokens = tokenService.findByUser(user);
        List<Token> refreshTokens = tokens.stream().filter(x -> x.getTokenType() == TokenType.REFRESH).toList();

        if (refreshTokens.isEmpty()) {
            throw new InvalidTokenException(messageSource);
        }

        Token refreshToken = refreshTokens.getFirst();

        if (refreshToken == null) {
            throw new InvalidTokenException(messageSource);
        }

        String refreshTokenString;

        if (!jwtService.isTokenValid(refreshToken.getToken(), user)) {
            refreshTokenString = jwtService.generateRefreshToken(user);
            tokenService.saveToken(user, refreshTokenString, TokenType.REFRESH);
        } else {
            refreshTokenString = refreshToken.getToken();
        }

        PublicUserDTO publicUser = modelMapper.map(accessToken.getUser(), PublicUserDTO.class);

        return AuthenticationResponse
                .builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshTokenString)
                .user(publicUser)
                .build();
    }

    @Override
    public void attachAuthCookies(AuthenticationResponse authenticationResponse, Consumer<Cookie> cookieConsumer) {
        tokenService.attachAuthCookies(authenticationResponse, cookieConsumer);
    }

    /**
     * Resets the password for a user based on the provided token and new password.
     */
    public void resetPassword(String token, String newPassword) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        User user = verificationToken.getUser();
        if (user == null) {
            throw new InvalidTokenException(messageSource);
        }
        verificationToken.setCreatedAt(LocalDateTime.now());

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
