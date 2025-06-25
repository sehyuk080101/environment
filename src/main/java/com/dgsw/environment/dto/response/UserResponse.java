package com.dgsw.environment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
