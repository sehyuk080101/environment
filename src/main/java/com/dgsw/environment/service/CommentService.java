package com.dgsw.environment.service;

import com.dgsw.environment.dto.CommentDTO;
import com.dgsw.environment.dto.UpdateCommentDTO;
import com.dgsw.environment.entity.CommentEntity;
import com.dgsw.environment.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public String writeComment(String articleId, CommentDTO commentDTO) {
        if (commentRepository.existsByCommentIdAndArticleId(commentDTO.getCommentId(), articleId)) {
            throw new RuntimeException("해당 ID를 가진 댓글이 이미 존재합니다.");
        }

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setArticleId(articleId);
        commentEntity.setCommentId(commentDTO.getCommentId());
        commentEntity.setContent(commentDTO.getContent());

        commentRepository.save(commentEntity);

        return "성공적으로 댓글을 작성했습니다.";
    }

    public String deleteComment(String articleId, String commentId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findByCommentIdAndArticleId(commentId, articleId);

        if (optionalCommentEntity.isEmpty()) {
            return "게시글이 존재하지 않습니다.";
        }

        commentRepository.deleteById(commentId);

        return "성공적으로 댓글을 삭제했습니다.";
    }

    public List<CommentDTO> getAllComments(String articleId) {
        List<CommentEntity> commentEntities = commentRepository.findAllByArticleId(articleId);

        return commentEntities.stream().map(comment -> new CommentDTO(comment.getCommentId(), comment.getArticleId(), comment.getContent())).collect(Collectors.toList());
    }

    public CommentDTO getComment(String articleId, String commentId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findByCommentIdAndArticleId(commentId, articleId);

        if (optionalCommentEntity.isEmpty()) {
            throw new RuntimeException("댓글이 존재하지 않습니다.");
        }

        CommentEntity commentEntity = optionalCommentEntity.get();

        return new CommentDTO(commentEntity.getCommentId(), commentEntity.getArticleId(), commentEntity.getContent());
    }

    public String updateComment(String articleId, String commentId, UpdateCommentDTO updateCommentDTO) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findByCommentIdAndArticleId(commentId, articleId);

        if (optionalCommentEntity.isEmpty()) {
            throw new RuntimeException("댓글이 존재하지 않습니다.");
        }

        if (updateCommentDTO.getContent().isBlank()) {
            throw new RuntimeException("내용은 공백이 될 수 없습니다.");
        }

        CommentEntity commentEntity = optionalCommentEntity.get();

        commentEntity.setContent(updateCommentDTO.getContent());

        commentRepository.save(commentEntity);

        return "성공적으로 댓글을 수정했습니다.";
    }
}
