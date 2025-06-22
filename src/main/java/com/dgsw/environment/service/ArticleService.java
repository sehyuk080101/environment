package com.dgsw.environment.service;

import com.dgsw.environment.dto.ArticleDTO;
import com.dgsw.environment.dto.UpdateArticleDTO;
import com.dgsw.environment.entity.ArticleEntity;
import com.dgsw.environment.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public String writeArticle(ArticleDTO articleDTO){
        ArticleEntity articleEntity=new ArticleEntity();
        articleEntity.setArticleId(articleDTO.getArticleId());
        articleEntity.setTitle(articleDTO.getTitle());
        articleEntity.setContent(articleDTO.getContent());

        if(articleRepository.existsById(articleDTO.getArticleId())){
            throw new RuntimeException("게시글이 이미 존재합니다.");
        }

        articleRepository.save(articleEntity);

        return "성공적으로 게시글을 작성했습니다.";
    }

    public ArticleDTO getArticle(String articleId){
        ArticleEntity articleEntity=articleRepository.findById(articleId).orElse(null);

        if(articleEntity==null){
            throw new RuntimeException("게시글이 존재하지 않습니다.");
        }

        return new ArticleDTO(articleEntity.getArticleId(),articleEntity.getTitle(),articleEntity.getContent());
    }

    public List<ArticleDTO> getAllArticles(){
        List<ArticleEntity> articleEntities=articleRepository.findAll();

        return articleEntities.stream().map(article -> new ArticleDTO(article.getArticleId(),article.getTitle(),article.getContent())).toList();
    }

    public String updateArticle(String articleId, UpdateArticleDTO updateArticleDTO){
        ArticleEntity articleEntity=articleRepository.findById(articleId).orElse(null);

        if(articleEntity==null){
            throw new RuntimeException("게시글이 존재하지 않습니다.");
        }
        if(updateArticleDTO.getTitle().isBlank()){
            throw new RuntimeException("제목은 공백이 될 수 없습니다.");
        }
        if(updateArticleDTO.getContent().isBlank()){
            throw new RuntimeException("내용은 공백이 될 수 없습니다.");
        }

        articleEntity.setTitle(updateArticleDTO.getTitle());
        articleEntity.setContent(updateArticleDTO.getContent());

        articleRepository.save(articleEntity);

        return "성공적으로 게시글을 수정했습니다.";
    }

    public String deleteArticle(String articleId){
        ArticleEntity articleEntity=articleRepository.findById(articleId).orElse(null);

        if(articleEntity==null){
            throw new RuntimeException("게시글이 존재하지 않습니다.");
        }

        articleRepository.delete(articleEntity);

        return "성공적으로 게시글을 삭제했습니다.";
    }
}
