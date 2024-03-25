package com.mat.taksov.security;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    @Value("${spring.security.cors.allowed-origins}")
    private String[] allowedOrigins;
    @Value("${spring.security.csrf}")
    private boolean csrfEnabled;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        if(allowedOrigins != null && allowedOrigins.length > 0) http.cors(Customizer.withDefaults());
        if(!csrfEnabled) http.csrf( csrf -> csrf.disable());
        return http
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers(
                                        "/auth/refresh_token",
                                        "/auth/login",
                                        "/auth/signup",
                                        "/home",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/bus/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                                .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())// requiere que el enum tenga ROLE_ADMIN, usar hasAuthority
                                .anyRequest().authenticated()
                        )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
     }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowCredentials(true);
         configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "OPTIONS"));
         configuration.setAllowedHeaders(Arrays.asList("Authorization", "Origin",
                 "Access-Control-Allow-Origin",
                 "Content-Type",
                 "Accept"
         ));
        configuration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();    // despues probar reactive
         source.registerCorsConfiguration("/**", configuration);
         return source;
    }

}
