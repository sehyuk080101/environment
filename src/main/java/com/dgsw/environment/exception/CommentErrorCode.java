package com.dgsw.environment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NO_DELETE_PERMISSION("댓글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NO_EDIT_PERMISSION("댓글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    EMPTY_COMMENT_CONTENT("내용은 공백이 될 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
