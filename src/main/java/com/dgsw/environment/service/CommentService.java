package com.dgsw.environment.service;

import com.dgsw.environment.dto.CommentResponse;
import com.dgsw.environment.dto.CreateCommentRequest;
import com.dgsw.environment.dto.UpdateCommentRequest;
import com.dgsw.environment.entity.CommentEntity;
import com.dgsw.environment.entity.CommentView;
import com.dgsw.environment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void writeComment(String articleId, CreateCommentRequest request, String authorId) {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setArticleId(articleId);
        commentEntity.setAuthorId(authorId);
        commentEntity.setId(UUID.randomUUID().toString());
        commentEntity.setContent(request.getContent());

        commentRepository.save(commentEntity);
    }

    public void deleteComment(String articleId, String commentId, String authorId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findByIdAndArticleId(commentId, articleId);

        if (optionalCommentEntity.isEmpty()) {
            throw new RuntimeException("댓글이 존재하지 않습니다.");
        }

        CommentEntity commentEntity = optionalCommentEntity.get();

        if (!commentEntity.getAuthorId().equals(authorId)) {
            throw new RuntimeException("댓글을 삭제할 권한이 없습니다.");
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
            throw new RuntimeException("댓글이 존재하지 않습니다.");
        }

        CommentEntity commentEntity = optionalCommentEntity.get();

        if (!commentEntity.getAuthorId().equals(authorId)) {
            throw new RuntimeException("댓글을 수정할 권한이 없습니다.");
        }

        if (request.getContent().isBlank()) {
            throw new RuntimeException("내용은 공백이 될 수 없습니다.");
        }

        commentEntity.setContent(request.getContent());

        commentRepository.save(commentEntity);
    }
}
