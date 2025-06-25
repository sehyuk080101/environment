package com.dgsw.environment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    private String articleId;
    private String content;
    private String password;
}
