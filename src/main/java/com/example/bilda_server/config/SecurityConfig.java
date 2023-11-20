package com.example.bilda_server.config;

import com.example.bilda_server.jwt.filter.CustomUsernamePasswordAuthenticationFilter;
import com.example.bilda_server.utils.RequestURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(form -> form.loginPage(RequestURI.USER_REQUEST_PREFIX+"/login")
	.permitAll()
	.defaultSuccessUrl("/home", true)
	.failureUrl("/login?error=page"))
            .authorizeHttpRequests(
	auth -> auth.requestMatchers(RequestURI.USER_REQUEST_PREFIX + "/login").permitAll()
	    .anyRequest().authenticated())
            .addFilterAt(CustomUsernamePasswordAuthenticationFilter.getInstance(),
	UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
