package com.dgsw.environment.service;

import com.dgsw.environment.dto.ArticleDetailResponse;
import com.dgsw.environment.dto.ArticleResponse;
import com.dgsw.environment.dto.CreateArticleRequest;
import com.dgsw.environment.dto.UpdateArticleRequest;
import com.dgsw.environment.entity.ArticleEntity;
import com.dgsw.environment.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void writeArticle(CreateArticleRequest request) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setArticleId(UUID.randomUUID().toString());
        articleEntity.setTitle(request.getTitle());
        articleEntity.setContent(request.getContent());

        articleRepository.save(articleEntity);
    }

    public ArticleDetailResponse getArticle(String articleId) {
        ArticleEntity articleEntity = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        return new ArticleDetailResponse();
    }

    public List<ArticleResponse> getAllArticles() {
        List<ArticleEntity> articleEntities = articleRepository.findAll();

        return articleEntities.stream().map(article -> new ArticleResponse(article.getArticleId(), article.getTitle())).toList();
    }

    public void updateArticle(String articleId, UpdateArticleRequest updateArticleDTO) {
        ArticleEntity articleEntity = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (updateArticleDTO.getTitle().isBlank()) {
            throw new RuntimeException("제목은 공백이 될 수 없습니다.");
        }

        if (updateArticleDTO.getContent().isBlank()) {
            throw new RuntimeException("내용은 공백이 될 수 없습니다.");
        }

        articleEntity.setTitle(updateArticleDTO.getTitle());
        articleEntity.setContent(updateArticleDTO.getContent());

        articleRepository.save(articleEntity);
    }

    public void deleteArticle(String articleId) {
        ArticleEntity articleEntity = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        articleRepository.delete(articleEntity);
    }
}
