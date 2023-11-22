package com.example.bilda_server.jwt.filter;

import com.example.bilda_server.request.SignInRequest;
import com.example.bilda_server.utils.RequestURI;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URI = RequestURI.USER_REQUEST_PREFIX + "/login";
    private static final String HTTP_METHOD = "POST";
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    private static final List<String> CONTENT_TYPE = Arrays.asList(
        "application/json",
        "application/json; charset=UTF-8",
        "application/json;charset=UTF-8");

    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
        new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URI, HTTP_METHOD);

    public CustomLoginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException, IOException {
        String contentType = request.getContentType();
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        if (contentType == null || !CONTENT_TYPE.contains(contentType)) {
            throw new AuthenticationServiceException(
                "지원되지 않는 Content-Type입니다. " + contentType);
        }

        SignInRequest mappedBody = objectMapper.readValue(body, SignInRequest.class);
        String email = mappedBody.email();
        String password = mappedBody.password();

        UsernamePasswordAuthenticationToken authorizedRequest = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authorizedRequest);
    }
}
