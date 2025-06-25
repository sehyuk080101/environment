package com.dgsw.environment.service;

import com.dgsw.environment.dto.*;
import com.dgsw.environment.entity.UserEntity;
import com.dgsw.environment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(CreateUserRequest request) {
        if (userRepository.existsById(request.getId())) {
            throw new RuntimeException("이미 존재하는 유저입니다.");
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId(request.getId());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());

        userRepository.save(userEntity);
    }

    public LoginResponse login(LoginRequest request) {

    }

    public TokenResponse refresh(RefreshRequest request) {

    }

    public UserResponse getUserInfo(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        return new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getCreatedAt(), userEntity.getUpdatedAt());
    }
}
