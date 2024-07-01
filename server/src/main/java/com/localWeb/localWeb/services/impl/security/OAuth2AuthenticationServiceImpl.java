package com.localWeb.localWeb.services.impl.security;

import com.localWeb.localWeb.models.dto.auth.AuthenticationResponse;
import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.security.CustomOAuth2User;
import com.localWeb.localWeb.services.OAuth2AuthenticationService;
import com.localWeb.localWeb.services.TokenService;
import com.localWeb.localWeb.services.UserService;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * Implementation of the OAuth2AuthenticationService interface responsible for processing OAuth2 user authentication.
 * This services handles the post-login process, including token generation and cookie attachment.
 */
@Service
@AllArgsConstructor
public class OAuth2AuthenticationServiceImpl implements OAuth2AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public boolean processOAuthPostLogin(CustomOAuth2User oAuth2User, Consumer<Cookie> addCookieFunc) {
        // Process OAuth2 user and retrieve associated user entity
        User user = userService.processOAuthUser(oAuth2User);

        tokenService.revokeAllUserTokens(user);

        // Generate authentication response and attach cookies to the response
        AuthenticationResponse authResponse = tokenService.generateAuthResponse(user);
        tokenService.attachAuthCookies(authResponse, addCookieFunc);

        // Check if additional user info is required
        return user.isAdditionalInfoRequired();
    }
}
