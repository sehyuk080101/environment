package com.dgsw.environment.controller;

import com.dgsw.environment.dto.*;
import com.dgsw.environment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> writeComment(@PathVariable String articleId, @RequestBody CreateCommentRequest request) {
        commentService.writeComment(articleId, request, "2419");

        return BaseResponse.created("성공적으로 댓글을 작성했습니다.");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> deleteComment(@PathVariable String articleId, @PathVariable String commentId) {
        commentService.deleteComment(articleId, commentId, "2419");

        return BaseResponse.ok("성공적으로 댓글을 삭제했습니다.");
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentsByArticleId(@PathVariable String articleId) {
        return BaseResponse.ok(commentService.getAllComments(articleId), "성공적으로 댓글 목록을 조회했습니다.");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> updateCommentByArticleId(
            @PathVariable String articleId,
            @PathVariable String commentId,
            @RequestBody UpdateCommentRequest request
    ) {
        commentService.updateComment(articleId, commentId, request, "2419");

        return BaseResponse.ok("성공적으로 댓글을 수정했습니다.");
    }
}
