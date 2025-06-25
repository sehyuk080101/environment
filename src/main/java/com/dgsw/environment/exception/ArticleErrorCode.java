package com.dgsw.environment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ArticleErrorCode implements ErrorCode {
    ARTICLE_NOT_FOUND("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),;

    private final String message;
    private final HttpStatus status;
}
