package com.dgsw.environment.global.security.token;

import com.dgsw.environment.domain.auth.dto.response.TokenResponse;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.exception.TokenErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider implements TokenProvider {
    private final JwtProperties jwtProperties;
    private SecretKey key;
    private JwtParser parser;

    @PostConstruct
    protected void init() {
        key = new SecretKeySpec(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );

        parser = Jwts.parser().verifyWith(key).build();
    }

    @Override
    public TokenResponse generateTokens(String userId) {
        Date now = new Date();

        Duration accessDuration = Duration.ofSeconds(jwtProperties.getDuration().access());
        Duration refreshDuration = Duration.ofSeconds(jwtProperties.getDuration().refresh());
        Date accessExpiration = new Date(now.getTime() + accessDuration.toMillis());
        Date refreshExpiration = new Date(now.getTime() + refreshDuration.toMillis());

        String accessToken = buildJwt(userId, now, accessExpiration, TokenPurpose.ACCESS);
        String refreshToken = buildJwt(userId, now, refreshExpiration, TokenPurpose.REFRESH);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public TokenPurpose getTokenPurpose(String token) {
        return TokenPurpose.from(parseClaims(token).get("purpose", String.class));
    }

    private Claims parseClaims(String token) {
        try {
            return parser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new CustomException(TokenErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(TokenErrorCode.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new CustomException(TokenErrorCode.MALFORMED_TOKEN);
        } catch (SignatureException e) {
            throw new CustomException(TokenErrorCode.INVALID_SIGNATURE);
        } catch (IllegalArgumentException e) {
            throw new CustomException(TokenErrorCode.INVALID_TOKEN);
        }
    }

    private String buildJwt(String userId, Date now, Date expiration, TokenPurpose tokenPurpose) {
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claims()
                .issuedAt(now)
                .expiration(expiration)
                .subject(userId)
                .add("purpose", tokenPurpose.name().toLowerCase())
                .and()
                .signWith(key)
                .compact();
    }
}
