package com.example.bilda_server.jwt.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUsernamePasswordAuthenticationFilter extends
    UsernamePasswordAuthenticationFilter {

    private static CustomUsernamePasswordAuthenticationFilter instance = new CustomUsernamePasswordAuthenticationFilter();

    public static CustomUsernamePasswordAuthenticationFilter getInstance() {
        return instance;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
	"Authentication method not supported: " + request.getMethod());
        }
        String email = request.getParameter("email");
        email = email != null ? email : "";
        String password = request.getParameter("password");
        password = password != null ? password : "";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            email, password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
