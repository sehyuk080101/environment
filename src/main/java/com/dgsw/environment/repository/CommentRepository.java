package com.dgsw.environment.repository;

import com.dgsw.environment.entity.CommentEntity;
import com.dgsw.environment.entity.CommentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {
    Optional<CommentEntity> findByIdAndArticleId(String commentId, String articleId);

    @Query(value = """
                SELECT
                    c.id as id,
                    c.content as content,
                    u.id as authorId,
                    u.username as authorName
                FROM CommentEntity c
                JOIN UserEntity u ON c.authorId = u.id
                WHERE c.articleId = :articleId
            """)
    List<CommentView> findCommentViewsByArticleId(@Param("articleId") String articleId);
}
