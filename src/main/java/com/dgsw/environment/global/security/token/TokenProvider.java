package com.dgsw.environment.global.security.token;

import com.dgsw.environment.dto.response.TokenResponse;

public interface TokenProvider {
    TokenResponse generateTokens(String userId);

    String getUserId(String token);

    TokenPurpose getTokenPurpose(String token);
}
