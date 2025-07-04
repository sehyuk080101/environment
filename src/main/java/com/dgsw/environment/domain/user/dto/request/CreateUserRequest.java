package com.dgsw.environment.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRequest {
    private String id;
    private String username;
    private String password;
}
