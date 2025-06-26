package com.dgsw.environment.global.security.token;

import com.dgsw.environment.domain.auth.dto.response.TokenResponse;

public interface TokenProvider {
    TokenResponse generateTokens(String userId);

    String getUserId(String token);

    TokenPurpose getTokenPurpose(String token);
}
