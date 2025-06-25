package com.dgsw.environment.controller;

import com.dgsw.environment.dto.*;
import com.dgsw.environment.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseDTO writeArticle(@RequestBody CreateArticleRequest request) {
        articleService.writeArticle(request);

        return new ResponseDTO("성공적으로 게시글을 작성하였습니다.");
    }

    @GetMapping("/{articleId}")
    public ArticleDetailResponse getArticle(@PathVariable String articleId) {
        return articleService.getArticle(articleId);
    }

    @GetMapping
    public List<ArticleResponse> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PutMapping("/{articleId}")
    public ResponseDTO updateArticle(@PathVariable String articleId, @RequestBody UpdateArticleRequest updateArticleDTO) {
        articleService.updateArticle(articleId, updateArticleDTO);

        return new ResponseDTO("성공적으로 게시글을 수정했습니다.");
    }

    @DeleteMapping("/{articleId}")
    public ResponseDTO deleteArticle(@PathVariable String articleId) {
        articleService.deleteArticle(articleId);

        return new ResponseDTO("성공적으로 게시글을 삭제했습니다.");
    }
}
