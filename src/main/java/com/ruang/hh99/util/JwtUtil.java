package com.ruang.hh99.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:mySecretKey123123123}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}")        // 24시간, 밀리초 기준
    private long jwtExpirationMs;


    // JWT 토큰에서 사용할 키 생성
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("username", username)        // 사용자명을 claim으로 추가
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰에서 사용자 ID 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    // JWT 토큰에서 사용자명 추출
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("username", String.class);
    }

    // JWT 토큰에서 만료시간 추출
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    // JWT 토큰에서 Claims 추출
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 JWT 토큰입니다" + e.getMessage());
        }
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            System.err.println("유효하지 않은 JWT 서명입니다: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("유효하지 않은 JWT 토큰입니다: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("만료된 JWT 토큰입니다: " + e.getMessage());
        } catch (UnsupportedJwtException  e) {
            System.err.println("지원되지 않은 JWT 토큰입니다: " + e.getMessage());
        } catch (IllegalArgumentException  e) {
            System.err.println("JWT 토큰이 비어있습니다: " + e.getMessage());
        }
        return false;
    }

    // JWT 토큰 만료 확인
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Authorization 헤더에서 토큰 추출 ("Bearer" 제거)
    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new RuntimeException("유효하지 않은 Authorization 헤더입니다.");
    }
}



















