package com.dgsw.environment.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
