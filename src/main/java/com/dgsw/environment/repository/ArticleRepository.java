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
    @Query(value = "SELECT a.id, a.title, a.content FROM article a WHERE a.id = :id LIMIT 1", nativeQuery = true)
    Optional<ArticleDetailView> findArticleViewByArticleId(@Param("id") String id);

    @Query(value = """
            SELECT a.id as id, a.authorId as authorId, u.username as authorName, a.title as title, a.updatedAt as updatedAt
            FROM ArticleEntity a
            JOIN UserEntity u
            ON a.authorId = u.id
            """)
    List<ArticleView> findAllProjectBy();
}