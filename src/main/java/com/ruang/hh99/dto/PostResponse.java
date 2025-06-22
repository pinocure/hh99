package com.ruang.hh99.dto;

import java.time.LocalDateTime;

public class PostResponse {

    private String id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // 생성자
    public PostResponse() {}

    // 게시글 목록용 생성자 (content 제외)
    public PostResponse(String id, String title, String author, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
    }

    // 전체 정보 생성자
    public PostResponse(String id, String title, String author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    // 수정 시간 포함 생성자
    public PostResponse(String id, String title, String author, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // getter & setter
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }




}


















