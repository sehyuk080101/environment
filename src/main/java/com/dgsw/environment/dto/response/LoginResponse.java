package com.dgsw.environment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private TokenResponse tokenResponse;
    private String username;
}
