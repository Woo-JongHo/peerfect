package com.peerfect.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "GPvh5BzbvJifIdI5sfW8YhRB/XfW5HX9R5PQo7g6U2Mlk8ncHB4CY0SAH9ktg0/9k/GBUrGxNxBWuqmv1cbwVA==";
    private static final String REFRESH_TOKEN_SECRET_KEY = "GPvh5BzbvJifIdI5sfW8YhRB/XfW5HX9R5PQo7g6U2Mlk8ncHB4CY0SAH9ktg0/9k/GBUrGxNxBWuqmv1cbwVA==";

    private static final byte[] SECRET_KEY_BYTES = Base64.getDecoder().decode(SECRET_KEY);
    private static final byte[] REFRESH_TOKEN_SECRET_KEY_BYTES = Base64.getDecoder().decode(REFRESH_TOKEN_SECRET_KEY);

    private static final long ACCESS_TOKEN_VALIDITY = 3600;
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60; // 7일 (초 단위)

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");


    public String generateAccessToken(String memberId) {
        ZonedDateTime now = ZonedDateTime.now(KOREA_ZONE);
        ZonedDateTime expiry = now.plusSeconds(ACCESS_TOKEN_VALIDITY);

        // 로그 출력 (JWT 생성 시각 및 만료 시각 확인)
        log.info("🕒 [JWT 생성] 현재 시간(한국 기준): {}", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("🕒 [JWT 생성] 만료 시간(한국 기준): {}", expiry.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expiry.toInstant()))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY_BYTES), SignatureAlgorithm.HS512)
                .compact();
    }

    // Refresh Token 생성 (한국 시간 기준)
    public String generateRefreshToken(String memberId) {
        ZonedDateTime now = ZonedDateTime.now(KOREA_ZONE);
        ZonedDateTime expiry = now.plusSeconds(REFRESH_TOKEN_VALIDITY);

        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expiry.toInstant()))
                .signWith(Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY_BYTES), SignatureAlgorithm.HS512)
                .compact();
    }

    // Refresh Token 검증
    public boolean validateRefreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY_BYTES))
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
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
            throw new RuntimeException("Invalid token", e);
        }
    }
}
