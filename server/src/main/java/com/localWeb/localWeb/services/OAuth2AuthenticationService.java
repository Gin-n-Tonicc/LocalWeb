package com.localWeb.localWeb.services;

import com.localWeb.localWeb.security.CustomOAuth2User;
import jakarta.servlet.http.Cookie;

import java.util.function.Consumer;

public interface OAuth2AuthenticationService {
    boolean processOAuthPostLogin(CustomOAuth2User oAuth2User, Consumer<Cookie> addCookieFunc) throws Exception;
}
