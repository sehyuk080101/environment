package com.dgsw.environment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String id;
    private Author author;
    private String content;

    public static class Author {
        private String id;
        private String name;
    }
}
