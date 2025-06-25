package com.dgsw.environment.controller;

import com.dgsw.environment.dto.request.CreateCommentRequest;
import com.dgsw.environment.dto.request.UpdateCommentRequest;
import com.dgsw.environment.global.response.BaseResponse;
import com.dgsw.environment.dto.response.CommentResponse;
import com.dgsw.environment.global.annotation.CurrentUserId;
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
    public ResponseEntity<BaseResponse<Void>> writeComment(
            @PathVariable String articleId,
            @RequestBody CreateCommentRequest request,
            @CurrentUserId String authorId
    ) {
        commentService.writeComment(articleId, request, authorId);

        return BaseResponse.created("성공적으로 댓글을 작성했습니다.");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> deleteComment(
            @PathVariable String articleId,
            @PathVariable String commentId,
            @CurrentUserId String authorId
    ) {
        commentService.deleteComment(articleId, commentId, authorId);

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
            @RequestBody UpdateCommentRequest request,
            @CurrentUserId String authorId
    ) {
        commentService.updateComment(articleId, commentId, request, authorId);

        return BaseResponse.ok("성공적으로 댓글을 수정했습니다.");
    }
}
