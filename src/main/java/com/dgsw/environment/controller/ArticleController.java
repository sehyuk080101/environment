package com.dgsw.environment.controller;

import com.dgsw.environment.dto.ArticleDTO;
import com.dgsw.environment.dto.ResponseDTO;
import com.dgsw.environment.dto.UpdateArticleDTO;
import com.dgsw.environment.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/articles")
public class ArticleController{
    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseDTO writeArticle(@RequestBody ArticleDTO articleDTO){
        String message=articleService.writeArticle(articleDTO);

        return new ResponseDTO(message);
    }

    @GetMapping("/{articleId}")
    public ArticleDTO getArticle(@PathVariable String articleId){
        return articleService.getArticle(articleId);
    }

    @GetMapping
    public List<ArticleDTO> getAllArticles(){
        return articleService.getAllArticles();
    }

    @PutMapping("/{articleId}")
    public ResponseDTO updateArticle(@PathVariable String articleId,@RequestBody UpdateArticleDTO updateArticleDTO){
        String message=articleService.updateArticle(articleId,updateArticleDTO);

        return new ResponseDTO(message);
    }

    @DeleteMapping("/{articleId}")
    public ResponseDTO deleteArticle(@PathVariable String articleId){
        String message=articleService.deleteArticle(articleId);

        return new ResponseDTO(message);
    }
}
