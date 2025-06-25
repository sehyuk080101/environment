package com.dgsw.environment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
