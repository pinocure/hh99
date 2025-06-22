package com.ruang.hh99.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserSignupRequest {

    @NotBlank(message = "사용자명은 필수입니다")
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "사용자명은 4자 이상 10자 이하의 알파벳 소문자와 숫자만 가능합니다")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$",
            message = "비밀번호는 8자 이상 15자 이하의 알파벳 대소문자와 숫자만 가능합니다")
    private String password;


    // 생성자
    public UserSignupRequest() {}

    public UserSignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    // getter & setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}






















