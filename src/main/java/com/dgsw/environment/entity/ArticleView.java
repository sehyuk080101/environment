package com.dgsw.environment.entity;

import java.time.LocalDateTime;

public interface ArticleView {
    String getId();

    String getAuthorId();

    String getAuthorName();

    String getTitle();

    LocalDateTime getUpdatedAt();
}
