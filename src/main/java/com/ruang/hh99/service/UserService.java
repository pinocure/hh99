package com.ruang.hh99.service;

import com.ruang.hh99.dto.UserLoginRequest;
import com.ruang.hh99.dto.UserSignupRequest;
import com.ruang.hh99.entity.User;
import com.ruang.hh99.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // 회원가입
    @Transactional
    public void signup(UserSignupRequest request) {
        // 중복 사용자 확인
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다");
        }

        // 사용자 생성 및 저장
        User user = new User(request.getUsername(), request.getPassword());
        userRepository.save(user);
    }

    // 로그인
    public User login(UserLoginRequest request) {
        // 사용자 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다"));

        // 비밀번호 확인
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return user;
    }

    // 사용자 ID로 사용자 조회
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
    }

    // 사용자명으로 사용자 조회
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
    }

}


























