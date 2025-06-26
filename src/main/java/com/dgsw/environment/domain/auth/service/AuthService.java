package com.dgsw.environment.domain.auth.service;

import com.dgsw.environment.domain.auth.dto.request.LoginRequest;
import com.dgsw.environment.domain.auth.dto.request.RefreshRequest;
import com.dgsw.environment.domain.auth.dto.response.LoginResponse;
import com.dgsw.environment.domain.auth.dto.response.TokenResponse;
import com.dgsw.environment.domain.user.repository.UserRepository;
import com.dgsw.environment.entity.UserEntity;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.exception.TokenErrorCode;
import com.dgsw.environment.exception.UserErrorCode;
import com.dgsw.environment.global.security.token.TokenProvider;
import com.dgsw.environment.global.security.token.TokenPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginResponse login(LoginRequest request) {
        String id = request.getId();
        UserEntity userEntity = getUserById(id);

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new CustomException(UserErrorCode.PASSWORD_MISMATCH);
        }

        TokenResponse tokenResponse = tokenProvider.generateTokens(id);

        return new LoginResponse(tokenResponse, userEntity.getUsername());
    }

    public TokenResponse refresh(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (tokenProvider.getTokenPurpose(refreshToken) != TokenPurpose.REFRESH) {
            throw new CustomException(TokenErrorCode.INVALID_TOKEN);
        }

        String userId = tokenProvider.getUserId(refreshToken);

        if (!userRepository.existsById(userId)) {
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }

        return tokenProvider.generateTokens(userId);
    }

    private UserEntity getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
