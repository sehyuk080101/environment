package com.dgsw.environment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class ArticleEntity {
    @Id
    private String articleId;

    private String title;

    private String content;
}