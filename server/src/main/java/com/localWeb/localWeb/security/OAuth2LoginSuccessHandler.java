package com.localWeb.localWeb.security;

import com.localWeb.localWeb.config.FrontendConfig;
import com.localWeb.localWeb.services.OAuth2AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2LoginSuccessHandler handles successful OAuth2 login authentication.
 * It redirects users to different URLs based on whether additional information is required after login.
 */
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final OAuth2AuthenticationService oAuth2AuthenticationService;
    private final FrontendConfig frontendConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Retrieve the OAuth2 user details from the authentication object
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        // Check if additional information is required after login
        boolean isAdditionalInfoRequired = false;
        try {
            isAdditionalInfoRequired = oAuth2AuthenticationService.processOAuthPostLogin(oauthUser, response::addCookie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Redirect users to appropriate URLs based on whether additional information is required
        if (isAdditionalInfoRequired) {
            response.sendRedirect(frontendConfig.getFinishRegisterUrl());
        } else {
            response.sendRedirect(frontendConfig.getBaseUrl());
        }
    }
}