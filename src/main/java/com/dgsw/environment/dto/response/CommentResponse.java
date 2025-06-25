package com.dgsw.environment.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private String id;
    private Author author;
    private String content;
    private LocalDateTime timestamp;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        private String id;
        private String name;
    }
}
