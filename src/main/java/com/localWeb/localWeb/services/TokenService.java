package com.localWeb.localWeb.services;

import com.localWeb.localWeb.enums.TokenType;
import com.localWeb.localWeb.models.dto.auth.AuthenticationResponse;
import com.localWeb.localWeb.models.entity.Token;
import com.localWeb.localWeb.models.entity.User;
import jakarta.servlet.http.Cookie;

import java.util.List;
import java.util.function.Consumer;

public interface TokenService {
    Token findByToken(String jwt);

    List<Token> findByUser(User user);

    void saveToken(User user, String jwtToken, TokenType tokenType);

    void revokeToken(Token token);

    void revokeAllUserTokens(User user);

    void logoutToken(String jwt);

    Cookie createJwtCookie(String jwt);

    Cookie createRefreshCookie(String refreshToken);

    AuthenticationResponse generateAuthResponse(User user);

    void attachAuthCookies(AuthenticationResponse authenticationResponse, Consumer<Cookie> cookieConsumer);

    void detachAuthCookies(Consumer<Cookie> cookieConsumer);
}
