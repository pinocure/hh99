package com.ruang.hh99.service;

import com.ruang.hh99.dto.PostRequest;
import com.ruang.hh99.dto.PostResponse;
import com.ruang.hh99.dto.PostUpdateRequest;
import com.ruang.hh99.entity.Post;
import com.ruang.hh99.entity.User;
import com.ruang.hh99.repository.PostRepository;
import com.ruang.hh99.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    // 전체 게시글 목록 조회 (작성날짜 기준 내림차순)
    public List<PostResponse> getAllPosts() {
        List<Object[]> results = postRepository.findAllPostsWithUserInfo();

        return results.stream()
                .map(result -> {
                    Post post = (Post) result[0];
                    String userName = (String) result[1];

                    return new PostResponse(
                            post.getPostId().toString(),
                            post.getTitle(),
                            userName,           // user 테이블의 username을 author로 사용
                            post.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());
    }

    // 게시글 작성
    @Transactional
    public PostResponse createPost(PostRequest request, Long userId) {
        System.out.println("=== 게시글 작성 디버깅 ===");
        System.out.println("받은 userId: " + userId);
        System.out.println("받은 title: " + request.getTitle());
        System.out.println("받은 content: " + request.getContent());


        // 사용자 유무 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        // 게시글 생성
        Post post = new Post(userId, request.getTitle(), request.getContent());
        Post savedPost = postRepository.save(post);

        return new PostResponse(
                savedPost.getPostId().toString(),
                savedPost.getTitle(),
                user.getUsername(),
                savedPost.getContent(),
                savedPost.getCreatedAt()
        );
    }

    // 선택한 게시글 조회
    public PostResponse getPost(Integer postId) {
        Object[] result = postRepository.findPostWithUserInfoById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Post post = (Post) result[0];
        String userName = (String) result[1];

        return new PostResponse(
                post.getPostId().toString(),
                post.getTitle(),
                userName,
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    // 게시글 수정
    @Transactional
    public PostResponse updatePost(Integer postId, PostUpdateRequest request, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));

        // 작성자 본인 확인
        if (!post.getUserId().equals(currentUserId)) {
            throw new RuntimeException("본인이 작성한 게시글만 수정할 수 있습니다");
        }

        // 게시글 수정
        post.updatePost(request.getTitle(), request.getContent());
        Post updatedPost = postRepository.save(post);

        // 작성자명 조회
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        return new PostResponse(
                updatedPost.getPostId().toString(),
                updatedPost.getTitle(),
                user.getUsername(),
                updatedPost.getContent(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt()
        );
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Integer postId, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));

        // 작성자 본인 확인
        if (!post.getUserId().equals(currentUserId)) {
            throw new RuntimeException("본인이 작성한 게시글만 삭제할 수 있습니다");
        }

        postRepository.delete(post);
    }

}






















