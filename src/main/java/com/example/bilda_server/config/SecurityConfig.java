package com.example.bilda_server.config;

import com.example.bilda_server.auth.CustomUserDetailsService;
import com.example.bilda_server.jwt.JwtTokenManager;
import com.example.bilda_server.jwt.filter.CustomLoginFilter;
import com.example.bilda_server.jwt.filter.JwtAuthenticationFilter;
import com.example.bilda_server.utils.RequestURI;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;
    private final JwtTokenManager jwtTokenManager;


    private final String[] allowedUrls = {"/api-docs/**", "/swagger-ui/**", "/v3/api-docs/**",
        "/swagger-resources/**", "/webjars/**", RequestURI.USER_REQUEST_PREFIX + "/signup",
        RequestURI.USER_REQUEST_PREFIX + "/signin", RequestURI.EMAIL_REQUEST_PREFIX + "/verify/**",
    RequestURI.EMAIL_REQUEST_PREFIX+"/**", RequestURI.CHAT_REQUEST_PREFIX+"/**"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
	.requestMatchers(allowedUrls).permitAll()
	.anyRequest().permitAll())
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
	SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter(),
	BasicAuthenticationFilter.class)
            .addFilterAfter(customLoginFilter(),
	LogoutFilter.class);

        return http.build();
    }

    @Bean
    public CustomLoginFilter customLoginFilter() {
        CustomLoginFilter customLoginFilter = new CustomLoginFilter(objectMapper,
            authenticationManager());

        customLoginFilter.setAuthenticationManager(authenticationManager());
        return customLoginFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenManager, customUserDetailsService);
    }
}
