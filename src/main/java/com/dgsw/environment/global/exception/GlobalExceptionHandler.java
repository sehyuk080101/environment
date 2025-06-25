package com.dgsw.environment.global.exception;

import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException e) {
        log.error(e.getMessage(), e);

        return ErrorResponse.of(e.getMessage(), e.getErrorCode().getStatus());
    }
}
