package com.dgsw.environment.entity;

import java.time.LocalDateTime;

public interface CommentView {
    String getId();

    String getContent();

    String getAuthorId();

    String getAuthorName();

    LocalDateTime getCreatedAt();
}
