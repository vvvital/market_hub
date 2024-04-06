package com.teamchallenge.markethub.config;

import com.teamchallenge.markethub.config.jwt.AuthEntryPointJwt;
import com.teamchallenge.markethub.config.jwt.AuthTokenFilter;
import com.teamchallenge.markethub.config.jwt.JwtUtils;
import com.teamchallenge.markethub.service.impl.UserServiceImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableCaching
public class SecurityConfig {

    private final UserServiceImpl userService;
    private final AuthEntryPointJwt authEntryPointJwt;
    private final JwtUtils jwtUtils;

    private final String[] postHttpRequests = new String[]{"/markethub/authorization",
            "/markethub/login", "markethub/users/reset_password", "markethub/users/{id}/change_password","/markethub/orders", "/markethub/goods/add"};

    private final String[] getHttpRequest = new String[]{"/markethub/del/{id}", "/markethub/all",
            "markethub/users/{id}/change_password", "/markethub/categories", "/markethub/categories/{category_id}",
            "/markethub/categories/{category_id}/sub-categories", "/markethub/categories/{category_id}/{filename}",
            "/markethub/goods/top-seller", "/markethub/goods/shares", "/markethub/goods/categories/{category_id}",
            "/markethub/goods/sub-categories/{sub_category_id}", "/markethub/goods/{item_id}", "/v3/api-docs", "/markethub/orders",
            "/swagger-ui/**","/swagger-resources/*","/v3/api-docs/**"};

    public SecurityConfig(UserServiceImpl userService, AuthEntryPointJwt authEntryPointJwt, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authEntryPointJwt = authEntryPointJwt;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, postHttpRequests).permitAll()
                        .requestMatchers(getHttpRequest).permitAll() // debug
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(except -> except.authenticationEntryPoint(authEntryPointJwt))
                .build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://market-hub-oleksandrs-projects-fa78f5ab.vercel.app",
                "http://localhost:3000", "https://market-hub-sigma.vercel.app","https://markethubstore.netlify.app",
                "https://markethubstore.netlify.com", "https://market-hub-sigma-liard.vercel.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
