package com.dgsw.environment.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();

    HttpStatus getStatus();
}
