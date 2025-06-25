package com.dgsw.environment.entity;

import com.dgsw.environment.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class CommentEntity extends BaseEntity {
    @Id
    private String id;

    private String articleId;

    private String authorId;

    private String content;
}
