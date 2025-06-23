package com.ruang.hh99.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthUtil {

    // 컨트롤러에서 사용할 헬퍼 클래스

    @Autowired
    private JwtUtil jwtUtil;

    // Authorization 헤더에서 사용자 ID 추출
    public Long getCurrentUserIdFromHeader(String authorizationHeader) {
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰");
        }
        return jwtUtil.getUserIdFromToken(token);
    }

    // Authorization 헤더에서 사용자명 추출
    public String getCurrentUsernameFromHeader(String authorizationHeader) {
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }
        return jwtUtil.getUsernameFromToken(token);
    }

    // 토큰 유효성 검사
    public boolean isValidToken(String authorizationHeader) {
        try {
            String token = jwtUtil.extractTokenFromHeader(authorizationHeader);
            return jwtUtil.validateToken(token);
        } catch (Exception e) {
            return false;
        }
    }
}
















