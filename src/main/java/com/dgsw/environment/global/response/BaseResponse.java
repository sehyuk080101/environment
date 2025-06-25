package com.dgsw.environment.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private T data;
    private String message;

    public BaseResponse(String message) {
        this.message = message;
    }

    public static <T> ResponseEntity<BaseResponse<T>> of(T data, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new BaseResponse<>(data, message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(T data, String message) {
        return of(data, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<BaseResponse<T>> created(T data, String message) {
        return of(data, message, HttpStatus.CREATED);
    }

    public static ResponseEntity<BaseResponse<Void>> of(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new BaseResponse<>(message));
    }

    public static ResponseEntity<BaseResponse<Void>> ok(String message) {
        return of(message, HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponse<Void>> created(String message) {
        return of(message, HttpStatus.CREATED);
    }
}
