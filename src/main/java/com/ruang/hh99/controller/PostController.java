package com.ruang.hh99.controller;

import com.ruang.hh99.dto.ApiResponse;
import com.ruang.hh99.dto.PostRequest;
import com.ruang.hh99.dto.PostResponse;
import com.ruang.hh99.dto.PostUpdateRequest;
import com.ruang.hh99.service.PostService;
import com.ruang.hh99.util.JwtAuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private JwtAuthUtil jwtAuthUtil;


    @GetMapping
    @Operation(summary = "1. 전체 게시글 조회")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts() {
        try {
            List<PostResponse> posts = postService.getAllPosts();
            return ResponseEntity.ok(ApiResponse.success("게시글 목록 조회 성공", posts));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("게시글 목록 조회 실패:" + e.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "2. 게시글 작성")
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @Valid @RequestBody PostRequest postRequest,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // JWT 토큰에서 userId 추출
            Long userId = jwtAuthUtil.getCurrentUserIdFromHeader(authorizationHeader);

            PostResponse createdPost = postService.createPost(postRequest, userId);
            return ResponseEntity.ok(ApiResponse.success("게시글 작성 성공", createdPost));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("게시글 작성 실패:" + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "3. 선택한 게시글 조회")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Integer id) {
        try {
            PostResponse post = postService.getPost(id);
            return ResponseEntity.ok(ApiResponse.success("게시글 조회 성공", post));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("게시글 조회 실패:" + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "4. 게시글 수정")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Integer id,
            @Valid @RequestBody PostUpdateRequest postUpdateRequest,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // JWT 토큰에서 userId 추출
            Long currentUserId = jwtAuthUtil.getCurrentUserIdFromHeader(authorizationHeader);

            PostResponse updatedPost = postService.updatePost(id, postUpdateRequest, currentUserId);
            return ResponseEntity.ok(ApiResponse.success("게시글 수정 성공", updatedPost));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("게시글 수정 실패:" + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "5. 게시글 삭제")
    public ResponseEntity<ApiResponse<String>> deletePost(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // JWT 토큰에서 userId 추출
            Long currentUserId = jwtAuthUtil.getCurrentUserIdFromHeader(authorizationHeader);

            postService.deletePost(id, currentUserId);
            return ResponseEntity.ok(ApiResponse.success("게시글 삭제 성공"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("게시글 삭제 실패" + e.getMessage()));
        }
    }

}

















