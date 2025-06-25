package com.dgsw.environment.entity;

import com.dgsw.environment.exception.CommentErrorCode;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class CommentEntity extends BaseEntity {
    @Id
    @Column(name = "comment_id", nullable = false)
    private String id;

    private String articleId;

    private String authorId;

    private String content;

    private CommentEntity(String id, String articleId, String authorId, String content) {
        this.id = id;
        this.articleId = articleId;
        this.authorId = authorId;
        this.content = content;
    }

    public static CommentEntity createComment(String articleId, String authorId, String content) {
        validateContent(content);

        return new CommentEntity(UUID.randomUUID().toString(), articleId, authorId, content);
    }

    public void changeContent(String content) {
        validateContent(content);

        this.content = content;
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new CustomException(CommentErrorCode.EMPTY_COMMENT_CONTENT);
        }
    }
}
