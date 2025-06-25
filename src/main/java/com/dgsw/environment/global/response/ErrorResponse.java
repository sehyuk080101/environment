package com.dgsw.environment.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;

    public static ResponseEntity<ErrorResponse> of(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorResponse(message));
    }
}
