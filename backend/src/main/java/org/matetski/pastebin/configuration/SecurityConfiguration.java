package org.matetski.pastebin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Value("${frontend.url}")
    private String frontendURL;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/oauth2/authorization/google").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/getPasteByURL/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl(frontendURL + "/profile", true)
                        .failureUrl(frontendURL + "/error/500/VHJ5IGFnYWluIGxhdGVyLg==")
                );
        return http.build();
    }
}