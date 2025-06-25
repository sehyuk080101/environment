package com.dgsw.environment.global.security;

import com.dgsw.environment.dto.TokenResponse;
import com.dgsw.environment.global.security.jwt.TokenPurpose;

public interface TokenProvider {
    TokenResponse generateTokens(String userId);

    String getUserId(String token);

    TokenPurpose getTokenPurpose(String token);
}
