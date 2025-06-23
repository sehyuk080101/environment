package com.dgsw.environment.controller;

import com.dgsw.environment.dto.*;
import com.dgsw.environment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseDTO writeComment(@PathVariable String articleId, @RequestBody CreateCommentRequest request) {
        commentService.writeComment(articleId, request, "2419");

        return new ResponseDTO("성공적으로 댓글을 작성했습니다.");
    }

    @DeleteMapping("/{commentId}")
    public ResponseDTO deleteComment(@PathVariable String articleId, @PathVariable String commentId) {
        commentService.deleteComment(articleId, commentId, "2419");

        return new ResponseDTO("성공적으로 댓글을 삭제했습니다.");
    }

    @GetMapping
    public List<CommentResponse> getCommentsByArticleId(@PathVariable String articleId) {
        return commentService.getAllComments(articleId);
    }

    @PutMapping("/{commentId}")
    public ResponseDTO updateCommentByArticleId(
            @PathVariable String articleId,
            @PathVariable String commentId,
            @RequestBody UpdateCommentRequest request
    ) {
        String message = commentService.updateComment(articleId, commentId, request, "2419");

        return new ResponseDTO(message);
    }
}
