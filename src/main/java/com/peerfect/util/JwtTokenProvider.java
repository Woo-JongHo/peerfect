package com.peerfect.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "GPvh5BzbvJifIdI5sfW8YhRB/XfW5HX9R5PQo7g6U2Mlk8ncHB4CY0SAH9ktg0/9k/GBUrGxNxBWuqmv1cbwVA==";
    private static final String REFRESH_TOKEN_SECRET_KEY = "GPvh5BzbvJifIdI5sfW8YhRB/XfW5HX9R5PQo7g6U2Mlk8ncHB4CY0SAH9ktg0/9k/GBUrGxNxBWuqmv1cbwVA==";

    private static final byte[] SECRET_KEY_BYTES = Base64.getDecoder().decode(SECRET_KEY);
    private static final byte[] REFRESH_TOKEN_SECRET_KEY_BYTES = Base64.getDecoder().decode(REFRESH_TOKEN_SECRET_KEY);

    private static final long ACCESS_TOKEN_VALIDITY = 3600000; // 15분
    private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7일

    // Access Token 생성
    public String generateAccessToken(String memberId) {
        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY_BYTES), SignatureAlgorithm.HS512)
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(String memberId) {
        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY_BYTES), SignatureAlgorithm.HS512)
                .compact();
    }

    // Refresh Token 검증 (Spring MVC 방식)
    public boolean validateRefreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            log.error("❌ Refresh token is missing in cookies!");
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY_BYTES)) // 올바른 키 사용
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("❌ Invalid refresh token: {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 사용자 ID 추출
    public String getMemberIdFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY_BYTES))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("❌ Invalid token: {}", e.getMessage());
            throw new RuntimeException("Invalid token", e);
        }
    }
}
