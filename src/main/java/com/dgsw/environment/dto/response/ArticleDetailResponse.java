package com.dgsw.environment.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ArticleDetailResponse {
    private String id;
    private Author author;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @AllArgsConstructor
    public static class Author {
        private String id;
        private String name;
    }
}
