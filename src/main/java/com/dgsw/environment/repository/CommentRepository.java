package com.dgsw.environment.repository;

import com.dgsw.environment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {
    List<CommentEntity> findAllByArticleId(String articleId);

    Optional<CommentEntity> findByCommentIdAndArticleId(String commentId, String articleId);

    boolean existsByCommentIdAndArticleId(String commentId, String articleId);
}
