package com.dgsw.environment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateArticleRequest {
    private String title;
    private String content;
}