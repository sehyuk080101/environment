package com.dgsw.environment.entity;

import com.dgsw.environment.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class ArticleEntity extends BaseEntity {
    @Id
    private String articleId;

    private String authorId;

    private String title;

    private String content;
}