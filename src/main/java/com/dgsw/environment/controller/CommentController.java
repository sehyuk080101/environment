package com.dgsw.environment.controller;

import com.dgsw.environment.dto.CommentDTO;
import com.dgsw.environment.dto.ResponseDTO;
import com.dgsw.environment.dto.UpdateCommentDTO;
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
    public ResponseDTO writeComment(@PathVariable String articleId, @RequestBody CommentDTO commentDTO) {
        String message = commentService.writeComment(articleId, commentDTO);

        return new ResponseDTO(message);
    }

    @DeleteMapping("/{commentId}")
    public ResponseDTO deleteComment(@PathVariable String articleId, @PathVariable String commentId) {
        String message = commentService.deleteComment(articleId, commentId);

        return new ResponseDTO(message);
    }

    @GetMapping("/{commentId}")
    public CommentDTO getComment(@PathVariable String articleId, @PathVariable String commentId) {
        return commentService.getComment(articleId, commentId);
    }

    @GetMapping
    public List<CommentDTO> getCommentsByArticleId(@PathVariable String articleId) {
        return commentService.getAllComments(articleId);
    }

    @PutMapping("/{commentId}")
    public ResponseDTO updateCommentByArticleId(@PathVariable String articleId, @PathVariable String commentId, @RequestBody UpdateCommentDTO updateCommentDTO) {
        String message = commentService.updateComment(articleId, commentId, updateCommentDTO);

        return new ResponseDTO(message);
    }
}
