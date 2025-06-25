package com.dgsw.environment.repository;

import com.dgsw.environment.entity.ArticleDetailView;
import com.dgsw.environment.entity.ArticleEntity;
import com.dgsw.environment.entity.ArticleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {
    @Query("""
                SELECT
                    a.id AS id,
                    a.authorId AS authorId,
                    u.username AS authorName,
                    a.title AS title,
                    a.content AS content,
                    a.createdAt AS createdAt,
                    a.updatedAt AS updatedAt
                FROM ArticleEntity a
                JOIN UserEntity u ON a.authorId = u.id
                WHERE a.id = :id
            """)
    Optional<ArticleDetailView> findArticleViewByArticleId(@Param("id") String id);

    @Query("""
            SELECT a.id as id, a.authorId as authorId, u.username as authorName, a.title as title, a.updatedAt as updatedAt
            FROM ArticleEntity a
            JOIN UserEntity u
            ON a.authorId = u.id
            """)
    List<ArticleView> findAllProjectBy();
}