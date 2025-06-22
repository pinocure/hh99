package com.ruang.hh99.controller;

import com.ruang.hh99.dto.ApiResponse;
import com.ruang.hh99.dto.UserLoginRequest;
import com.ruang.hh99.dto.UserSignupRequest;
import com.ruang.hh99.entity.User;
import com.ruang.hh99.service.UserService;
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

    @PostMapping("/signup")
    @Operation(summary = "2. 로그인")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserLoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest);

            // TODO: JWT 토큰 생성 로직 필요
            String token = "jwt-token-" + user.getId();

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
    public ResponseEntity<ApiResponse<String>> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            // TODO: JWT에서 userId 추출해야함
            Long userId = 1L;

            User user = userService.getUserById(userId);
            return ResponseEntity.ok(ApiResponse.success("사용자 정보 조회 성공", user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("사용자 정보 조회 실패:" + e.getMessage()));
        }
    }

}






















