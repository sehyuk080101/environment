package com.dgsw.environment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND("유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USER_ID("유저 ID가 이미 존재합니다.", HttpStatus.CONFLICT),
    INVALID_USER_ID_LENGTH("ID는 2글자 이하가 될 수 없습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;
}
