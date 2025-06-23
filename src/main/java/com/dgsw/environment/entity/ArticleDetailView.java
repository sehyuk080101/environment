package com.dgsw.environment.entity;

import java.time.LocalDateTime;

public interface ArticleDetailView {
    String getId();

    String getAuthorId();

    String getAuthorName();

    String getTitle();

    String getContent();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
