package com.ruang.hh99.dto;

import jakarta.validation.constraints.NotBlank;

public class PostUpdateRequest {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;


    // 생성자
    public PostUpdateRequest() {}

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }


    // getter & setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}


















