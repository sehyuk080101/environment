package com.dgsw.environment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    INVALID_TOKEN("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN("지원되지 않는 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_SIGNATURE("토큰 서명이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    MALFORMED_TOKEN("잘못된 형식의 토큰입니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;
}
