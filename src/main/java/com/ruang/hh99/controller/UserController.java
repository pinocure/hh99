package com.ruang.hh99.controller;

import com.ruang.hh99.dto.ApiResponse;
import com.ruang.hh99.dto.UserLoginRequest;
import com.ruang.hh99.dto.UserSignupRequest;
import com.ruang.hh99.entity.User;
import com.ruang.hh99.service.UserService;
import com.ruang.hh99.util.JwtAuthUtil;
import com.ruang.hh99.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtAuthUtil jwtAuthUtil;

    @PostMapping("/signup")
    @Operation(summary = "1. 회원가입")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody UserSignupRequest signupRequest) {
        try {
            userService.signup(signupRequest);
            return ResponseEntity.ok(ApiResponse.success("회원가입이 완료되었습니다"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("회원가입 실패:" + e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "2. 로그인")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserLoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest);

            // JWT 토큰 생성
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());

            // Authorization 헤더에 토큰 추가
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(ApiResponse.success("로그인 성공", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("로그인 실패" + e.getMessage()));
        }
    }

    @GetMapping("/user")
    @Operation(summary = "사용자 정보 조회")
    public ResponseEntity<ApiResponse<String>> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // JWT 토큰에서 사용자명 추출
            String username = jwtAuthUtil.getCurrentUsernameFromHeader(authorizationHeader);

            return ResponseEntity.ok(ApiResponse.success("사용자 정보 조회 성공", username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("사용자 정보 조회 실패:" + e.getMessage()));
        }
    }

}






















