package com.example.bilda_server.jwt.filter;

import com.example.bilda_server.auth.CustomUserDetailsService;
import com.example.bilda_server.jwt.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenManager jwtTokenManager,
        CustomUserDetailsService userDetailsService) {
        this.jwtTokenManager = jwtTokenManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        String accessToken = jwtTokenManager.extractAccessToken(request).orElse(null);
        String refreshToken = jwtTokenManager.extractRefreshToken(request).orElse(null);

        if (accessToken != null) {
            jwtTokenManager.validateAccessToken(accessToken);
            setAuthentication(accessToken);
        } else if (refreshToken != null) {
            jwtTokenManager.validateRefreshToken(refreshToken);
            jwtTokenManager.regenerateTokens(response, refreshToken);
        }

        // 둘다 유효하지 않으면 401 에러를 반환
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        String email = jwtTokenManager.getSubjectFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null,
	userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
