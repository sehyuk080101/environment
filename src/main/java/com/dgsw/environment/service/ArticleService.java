package com.dgsw.environment.service;

import com.dgsw.environment.dto.response.ArticleDetailResponse;
import com.dgsw.environment.dto.response.ArticleResponse;
import com.dgsw.environment.dto.request.CreateArticleRequest;
import com.dgsw.environment.dto.request.UpdateArticleRequest;
import com.dgsw.environment.entity.ArticleDetailView;
import com.dgsw.environment.entity.ArticleEntity;
import com.dgsw.environment.entity.ArticleView;
import com.dgsw.environment.exception.ArticleErrorCode;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void writeArticle(CreateArticleRequest request, String authorId) {
        ArticleEntity articleEntity = ArticleEntity.createArticle(authorId, request.getTitle(), request.getContent());

        articleRepository.save(articleEntity);
    }

    public ArticleDetailResponse getArticle(String articleId) {
        ArticleDetailView articleDetailView = articleRepository.findArticleViewByArticleId(articleId)
                .orElseThrow(() -> new CustomException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        return ArticleDetailResponse.builder()
                .id(articleDetailView.getId())
                .author(new ArticleDetailResponse.Author(articleDetailView.getAuthorId(), articleDetailView.getAuthorName()))
                .title(articleDetailView.getTitle())
                .content(articleDetailView.getContent())
                .createdAt(articleDetailView.getCreatedAt())
                .updatedAt(articleDetailView.getUpdatedAt())
                .build();
    }

    public List<ArticleResponse> getAllArticles() {
        List<ArticleView> articleViews = articleRepository.findAllProjectBy();

        return articleViews.stream().map(article ->
                ArticleResponse.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .author(new ArticleResponse.Author(article.getAuthorId(), article.getAuthorName()))
                        .timestamp(article.getTimestamp())
                        .build()
        ).toList();
    }

    public void updateArticle(String articleId, UpdateArticleRequest request, String authorId) {
        ArticleEntity articleEntity = getArticleById(articleId);

        if (!articleEntity.getAuthorId().equals(authorId)) {
            throw new CustomException(ArticleErrorCode.NO_EDIT_PERMISSION);
        }

        if (request.getTitle().isBlank()) {
            throw new CustomException(ArticleErrorCode.EMPTY_TITLE);
        }

        if (request.getContent().isBlank()) {
            throw new CustomException(ArticleErrorCode.EMPTY_CONTENT);
        }

        articleEntity.changeTitle(request.getTitle());
        articleEntity.changeContent(request.getContent());

        articleRepository.save(articleEntity);
    }

    public void deleteArticle(String articleId, String authorId) {
        ArticleEntity articleEntity = getArticleById(articleId);

        if (!articleEntity.getAuthorId().equals(authorId)) {
            throw new CustomException(ArticleErrorCode.NO_DELETE_PERMISSION);
        }

        articleRepository.delete(articleEntity);
    }

    private ArticleEntity getArticleById(String articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new CustomException(ArticleErrorCode.ARTICLE_NOT_FOUND));
    }
}
