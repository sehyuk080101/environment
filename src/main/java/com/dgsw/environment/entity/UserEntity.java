package com.dgsw.environment.entity;

import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.exception.UserErrorCode;
import com.dgsw.environment.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    private String id;
    private String username;
    private String password;

    private UserEntity(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static UserEntity createUser(String id, String username, String password) {
        validateId(id);
        validateUsername(username);
        validatePassword(password);

        return new UserEntity(id, username, password);
    }

    private static void validateId(String id) {
        if (id == null || id.length() < 3) {
            throw new CustomException(UserErrorCode.INVALID_USER_ID);
        }
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new CustomException(UserErrorCode.INVALID_USERNAME);
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new CustomException(UserErrorCode.INVALID_PASSWORD);
        }
    }

    public void changeUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public void changePassword(String password) {
        validatePassword(password);
        this.password = password;
    }
}
