package com.example.bilda_server.jwt;

import static com.example.bilda_server.utils.ExceptionMessage.EXPIRED_PERIOD_ACCESS_TOKEN;
import static com.example.bilda_server.utils.ExceptionMessage.EXPIRED_PERIOD_REFRESH_TOKEN;
import static com.example.bilda_server.utils.ExceptionMessage.INVALID_ACCESS_TOKEN;
import static com.example.bilda_server.utils.ExceptionMessage.INVALID_REFRESH_TOKEN;

import com.example.bilda_server.domain.entity.Tokens;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.repository.UserRepository;
import com.example.bilda_server.response.AuthorizedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenManager {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private static final String BEARER_MESSAGE = "Bearer ";

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.access.header}")
    private String accessTokenHeader;
    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;
    @Value("${jwt.refresh.header}")
    private String refreshTokenHeader;
    @Value("${jwt.issuer}")
    private String issuer;

    public Tokens generateTokens(final String subject) {
        final String accessToken = createToken(subject, accessTokenExpiration);
        final String refreshToken = createToken("", refreshTokenExpiration);
        return new Tokens(accessToken, refreshToken);
    }

    private String createToken(final String subject, final Long validityInMilliseconds) {
        final java.util.Date now = new java.util.Date();
        final java.util.Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setIssuer(issuer)
            .compact();
    }

    public void validateRefreshToken(final String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (final ExpiredJwtException e) {
            throw new IllegalArgumentException(EXPIRED_PERIOD_REFRESH_TOKEN);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_REFRESH_TOKEN);
        }
    }

    public void validateAccessToken(final String accessToken) {
        try {
            parseToken(accessToken);
        } catch (final ExpiredJwtException e) {
            throw new IllegalArgumentException(EXPIRED_PERIOD_ACCESS_TOKEN);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_ACCESS_TOKEN);
        }
    }


    private Jws<Claims> parseToken(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token);
    }


    public Optional<String> extractAccessToken(HttpServletRequest request) {
        String token = request.getHeader(accessTokenHeader);

        return extractToken(token);
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        String token = request.getHeader(refreshTokenHeader);

        return extractToken(token);
    }

    public Optional<String> extractToken(String token) {
        return Optional.ofNullable(token)
            .filter(tokenValue -> tokenValue.startsWith(BEARER_MESSAGE))
            .map(tokenValue -> tokenValue.replace(BEARER_MESSAGE, ""));
    }


    public String getSubjectFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public void regenerateTokens(HttpServletResponse response, String refreshToken)
        throws IOException {
        User user = userRepository.findByRefreshToken(refreshToken).orElse(null);
        final String newAccessToken = createToken(user.getEmail(), accessTokenExpiration);
        final String newRefreshToken = createToken("", refreshTokenExpiration);

        AuthorizedResponse authorizedResponse = new AuthorizedResponse(newAccessToken,
            newRefreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(authorizedResponse));
    }
}

