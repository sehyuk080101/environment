package com.dgsw.environment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {
    private String id;
    private Author author;
    private String title;
    private LocalDateTime timestamp;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        private String id;
        private String name;
    }
}
