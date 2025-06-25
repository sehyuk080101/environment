package com.dgsw.environment.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseDTO handleException(RuntimeException e) {
        log.error(e.getMessage(), e);

        return new ResponseDTO(e.getMessage());
    }
}
