package com.dgsw.environment.service;

import com.dgsw.environment.dto.request.CreateUserRequest;
import com.dgsw.environment.dto.request.LoginRequest;
import com.dgsw.environment.dto.request.RefreshRequest;
import com.dgsw.environment.dto.response.LoginResponse;
import com.dgsw.environment.dto.response.TokenResponse;
import com.dgsw.environment.dto.response.UserResponse;
import com.dgsw.environment.entity.UserEntity;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.exception.UserErrorCode;
import com.dgsw.environment.global.security.TokenProvider;
import com.dgsw.environment.global.security.jwt.TokenPurpose;
import com.dgsw.environment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public void signUp(CreateUserRequest request) {
        if (userRepository.existsById(request.getId())) {
            throw new RuntimeException("이미 존재하는 유저입니다.");
        }

        if (request.getId().length() < 3) {
            throw new RuntimeException("ID는 2글자 이하가 될 수 없습니다.");
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId(request.getId());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(userEntity);
    }

    public LoginResponse login(LoginRequest request) {
        UserEntity userEntity = getUserById(request.getUserId());

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다.");
        }

        TokenResponse tokenResponse = tokenProvider.generateTokens(request.getUserId());

        return new LoginResponse(tokenResponse, userEntity.getUsername());
    }

    public TokenResponse refresh(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (tokenProvider.getTokenPurpose(refreshToken) != TokenPurpose.REFRESH) {
            throw new RuntimeException("잘못된 토큰입니다.");
        }

        String userId = tokenProvider.getUserId(refreshToken);

        if (!userRepository.existsById(userId)) {
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }

        return tokenProvider.generateTokens(userId);
    }

    public UserResponse getUserInfo(String id) {
        UserEntity userEntity = getUserById(id);

        return new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getCreatedAt(), userEntity.getUpdatedAt());
    }

    private UserEntity getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
