package com.localWeb.localWeb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localWeb.localWeb.handlers.JwtAuthenticationEntryPoint;
import com.localWeb.localWeb.security.OAuth2LoginSuccessHandler;
import com.localWeb.localWeb.security.filters.JwtAuthenticationFilter;
import com.localWeb.localWeb.services.impl.security.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Configuration class for setting up security configurations, including authentication,
 * authorization, and OAuth2 integration.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final FrontendConfig frontendConfig;
    private final MessageSource messageSource;
    private final CustomOAuth2UserService oauthUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new JwtAuthenticationEntryPoint(objectMapper, messageSource));
                })
                // Configure authorization rules for various endpoints
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/files/upload",
                        "/api/v1/files/**",
                        "/api/v1/userCourses/**",
                        "/api/v1/messages/**",
                        "/api/v1/auth/**",
                        "/api/v1/auth/registrationConfirm/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/oauth/**",
                        "/api/v1/countries/**",
                        "/api/v1/cities/**",
                        "/api/v1/organisations/**",
                        "/api/v1/lessons/**",
                        "/api/v1/groups/**",
                        "/api/v1/applications/**"
                )
                .permitAll()
//                .requestMatchers(GET, "/api/v1/courses/**").permitAll()
//                .requestMatchers(POST, "/api/v1/courses/**").hasAnyRole(TEACHER.name(), ADMIN.name())
//                .requestMatchers(PUT, "/api/v1/courses/**").hasAnyRole(TEACHER.name(), ADMIN.name())
//                .requestMatchers(DELETE, "/api/v1/courses/**").hasAnyRole(TEACHER.name(), ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                // Configure OAuth2 login
                .oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauthUserService));
                    oauth2.loginPage(frontendConfig.getLoginUrl()).permitAll();
                    oauth2.successHandler(oAuth2LoginSuccessHandler);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer.logoutUrl("/api/v1/auth/logout");
                    httpSecurityLogoutConfigurer.addLogoutHandler(logoutHandler);
                    httpSecurityLogoutConfigurer.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                });

        return http.build();
    }
}
