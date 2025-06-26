package com.dgsw.environment.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshRequest {
    private String refreshToken;
}
