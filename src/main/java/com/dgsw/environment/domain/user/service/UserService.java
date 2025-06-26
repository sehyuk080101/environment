package com.dgsw.environment.domain.user.service;

import com.dgsw.environment.domain.user.dto.request.CreateUserRequest;
import com.dgsw.environment.domain.user.dto.request.UpdatePasswordRequest;
import com.dgsw.environment.domain.user.dto.request.UpdateUsernameRequest;
import com.dgsw.environment.domain.user.dto.response.UserResponse;
import com.dgsw.environment.entity.UserEntity;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.exception.UserErrorCode;
import com.dgsw.environment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(CreateUserRequest request) {
        if (userRepository.existsById(request.getId())) {
            throw new CustomException(UserErrorCode.DUPLICATE_USER_ID);
        }

        if (request.getId().length() < 3) {
            throw new CustomException(UserErrorCode.INVALID_USER_ID);
        }

        UserEntity userEntity = UserEntity.createUser(request.getId(), request.getUsername(), passwordEncoder.encode(request.getPassword()));

        userRepository.save(userEntity);
    }

    public UserResponse getUserInfo(String id) {
        UserEntity userEntity = getUserById(id);

        return new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getCreatedAt(), userEntity.getUpdatedAt());
    }

    public void updateUsername(UpdateUsernameRequest request, String userId) {
        UserEntity userEntity = getUserById(userId);

        userEntity.changeUsername(request.getUsername());

        userRepository.save(userEntity);
    }

    public void updatePassword(UpdatePasswordRequest request, String userId) {
        UserEntity userEntity = getUserById(userId);

        if (!passwordEncoder.matches(request.getOldPassword(), userEntity.getPassword())) {
            throw new CustomException(UserErrorCode.PASSWORD_MISMATCH);
        }

        userEntity.changePassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(userEntity);
    }

    private UserEntity getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
