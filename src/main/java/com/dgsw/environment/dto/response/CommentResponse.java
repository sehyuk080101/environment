package com.dgsw.environment.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {
    private String id;
    private Author author;
    private String content;
    private LocalDateTime timestamp;

    @Getter
    @AllArgsConstructor
    public static class Author {
        private String id;
        private String name;
    }
}
