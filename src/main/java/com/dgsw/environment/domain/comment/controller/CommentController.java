package com.dgsw.environment.domain.comment.controller;

import com.dgsw.environment.domain.comment.dto.request.CreateCommentRequest;
import com.dgsw.environment.domain.comment.dto.request.UpdateCommentRequest;
import com.dgsw.environment.global.response.BaseResponse;
import com.dgsw.environment.domain.comment.dto.response.CommentResponse;
import com.dgsw.environment.global.annotation.CurrentUserId;
import com.dgsw.environment.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "성공적으로 댓글을 작성했습니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> writeComment(
            @Parameter(description = "게시글 ID") @PathVariable String articleId,
            @RequestBody CreateCommentRequest request,
            @Parameter(hidden = true) @CurrentUserId String authorId
    ) {
        commentService.writeComment(articleId, request, authorId);
        return BaseResponse.created("성공적으로 댓글을 작성했습니다.");
    }

    @Operation(summary = "댓글 삭제", description = "게시글의 특정 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 댓글을 삭제했습니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> deleteComment(
            @Parameter(description = "게시글 ID") @PathVariable String articleId,
            @Parameter(description = "댓글 ID") @PathVariable String commentId,
            @Parameter(hidden = true) @CurrentUserId String authorId
    ) {
        commentService.deleteComment(articleId, commentId, authorId);
        return BaseResponse.ok("성공적으로 댓글을 삭제했습니다.");
    }

    @Operation(summary = "댓글 목록 조회", description = "특정 게시글의 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 댓글 목록을 조회했습니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentsByArticleId(
            @Parameter(description = "게시글 ID") @PathVariable String articleId
    ) {
        return BaseResponse.ok(commentService.getAllComments(articleId), "성공적으로 댓글 목록을 조회했습니다.");
    }

    @Operation(summary = "댓글 수정", description = "특정 게시글의 댓글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 댓글을 수정했습니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> updateCommentByArticleId(
            @Parameter(description = "게시글 ID") @PathVariable String articleId,
            @Parameter(description = "댓글 ID") @PathVariable String commentId,
            @RequestBody UpdateCommentRequest request,
            @Parameter(hidden = true) @CurrentUserId String authorId
    ) {
        commentService.updateComment(articleId, commentId, request, authorId);
        return BaseResponse.ok("성공적으로 댓글을 수정했습니다.");
    }
}
