package com.dgsw.environment.entity;

import com.dgsw.environment.exception.ArticleErrorCode;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "articles")
public class ArticleEntity extends BaseEntity {
    @Id
    private String articleId;

    private String authorId;

    private String title;

    private String content;

    private ArticleEntity(String articleId, String authorId, String title, String content) {
        this.articleId = articleId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    public static ArticleEntity createArticle(String articleId, String authorId, String title, String content) {
        validateTitle(title);
        validateContent(content);

        return new ArticleEntity(articleId, authorId, title, content);
    }

    public void changeTitle(String title) {
        validateTitle(title);

        this.title = title;
    }

    public void changeContent(String content) {
        validateContent(content);

        this.content = content;
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CustomException(ArticleErrorCode.EMPTY_TITLE);
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new CustomException(ArticleErrorCode.EMPTY_CONTENT);
        }
    }
}