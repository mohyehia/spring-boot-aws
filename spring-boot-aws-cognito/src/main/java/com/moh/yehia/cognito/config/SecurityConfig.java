package com.moh.yehia.cognito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/*/users/**").permitAll()
                                .requestMatchers("/api/*/pools/**").authenticated()
                ).exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
                    response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm = \"Restricted Content\"");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
                }))
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()))
                .build();
    }
}
