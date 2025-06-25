package com.dgsw.environment.global.security.token;

public enum TokenPurpose {
    ACCESS, REFRESH;

    public static TokenPurpose from(String value) {
        return TokenPurpose.valueOf(value.toUpperCase());
    }
}
