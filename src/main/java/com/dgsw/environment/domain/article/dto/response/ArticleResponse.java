package com.dgsw.environment.domain.article.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ArticleResponse {
    private String id;
    private Author author;
    private String title;
    private LocalDateTime timestamp;

    @Getter
    @AllArgsConstructor
    public static class Author {
        private String id;
        private String name;
    }
}
