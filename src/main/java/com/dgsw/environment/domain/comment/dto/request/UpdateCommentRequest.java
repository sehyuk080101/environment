package com.dgsw.environment.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCommentRequest {
    private String content;
}
