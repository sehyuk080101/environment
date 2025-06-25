package com.dgsw.environment.service;

import com.dgsw.environment.dto.response.CommentResponse;
import com.dgsw.environment.dto.request.CreateCommentRequest;
import com.dgsw.environment.dto.request.UpdateCommentRequest;
import com.dgsw.environment.entity.CommentEntity;
import com.dgsw.environment.entity.CommentView;
import com.dgsw.environment.exception.CommentErrorCode;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void writeComment(String articleId, CreateCommentRequest request, String authorId) {
        CommentEntity commentEntity = CommentEntity.createComment(articleId, authorId, request.getContent());

        commentRepository.save(commentEntity);
    }

    public void deleteComment(String articleId, String commentId, String authorId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findByIdAndArticleId(commentId, articleId);

        if (optionalCommentEntity.isEmpty()) {
            throw new CustomException(CommentErrorCode.COMMENT_NOT_FOUND);
        }

        CommentEntity commentEntity = optionalCommentEntity.get();

        if (!commentEntity.getAuthorId().equals(authorId)) {
            throw new CustomException(CommentErrorCode.NO_DELETE_PERMISSION);
        }

        commentRepository.delete(commentEntity);
    }

    public List<CommentResponse> getAllComments(String articleId) {
        List<CommentView> commentViews = commentRepository.findCommentViewsByArticleId(articleId);

        return commentViews.stream().map(comment ->
                CommentResponse.builder()
                        .id(comment.getId())
                        .author(new CommentResponse.Author(comment.getAuthorId(), comment.getAuthorName()))
                        .content(comment.getContent())
                        .timestamp(comment.getCreatedAt())
                        .build()
        ).toList();
    }

    public void updateComment(String articleId, String commentId, UpdateCommentRequest request, String authorId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findByIdAndArticleId(commentId, articleId);

        if (optionalCommentEntity.isEmpty()) {
            throw new CustomException(CommentErrorCode.COMMENT_NOT_FOUND);
        }

        CommentEntity commentEntity = optionalCommentEntity.get();

        if (!commentEntity.getAuthorId().equals(authorId)) {
            throw new CustomException(CommentErrorCode.NO_EDIT_PERMISSION);
        }

        if (request.getContent().isBlank()) {
            throw new CustomException(CommentErrorCode.EMPTY_COMMENT_CONTENT);
        }

        commentEntity.changeContent(request.getContent());

        commentRepository.save(commentEntity);
    }
}
