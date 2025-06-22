package com.ruang.hh99.repository;

import com.ruang.hh99.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // 전체 게시글 조회 (작성날짜 기준 내림차순 정렬)
    List<Post> findByTitle(String title);

    // 특정 사용자의 게시글 조회 (필요시 사용)
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);

    // 게시글과 작성자 정보 함께 조회 (ex. 신짱구의 모든 게시글)
    @Query("SELECT p FROM Post p JOIN User u ON p.userId = u.id ORDER BY p.createdAt DESC")
    List<Object[]> findAllPostsWithUserInfo();

    // 특정 게시글과 작성자 정보 조회 (ex. 신짱구의 3번 게시글)
    @Query("SELECT p, u.username FROM Post p JOIN User u ON p.userId = u.id WHERE p.postId = :postId")
    Optional<Object[]> findPostWithUserInfoById(@Param("postId") Integer postId);

}




















