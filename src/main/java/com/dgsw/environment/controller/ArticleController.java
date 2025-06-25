package com.dgsw.environment.controller;

import com.dgsw.environment.dto.request.CreateArticleRequest;
import com.dgsw.environment.dto.request.UpdateArticleRequest;
import com.dgsw.environment.dto.response.ArticleDetailResponse;
import com.dgsw.environment.dto.response.ArticleResponse;
import com.dgsw.environment.global.response.BaseResponse;
import com.dgsw.environment.global.annotation.CurrentUserId;
import com.dgsw.environment.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> writeArticle(@RequestBody CreateArticleRequest request, @CurrentUserId String authorId) {
        articleService.writeArticle(request, authorId);

        return BaseResponse.created("성공적으로 게시글을 작성하였습니다.");
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<BaseResponse<ArticleDetailResponse>> getArticle(@PathVariable String articleId) {
        return BaseResponse.ok(articleService.getArticle(articleId), "성공적으로 게시글을 조회했습니다.");
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ArticleResponse>>> getAllArticles() {
        return BaseResponse.ok(articleService.getAllArticles(), "성공적으로 게시글 목록을 조회했습니다.");
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<BaseResponse<Void>> updateArticle(
            @PathVariable String articleId,
            @RequestBody UpdateArticleRequest request,
            @CurrentUserId String authorId
    ) {
        articleService.updateArticle(articleId, request, authorId);

        return BaseResponse.ok("성공적으로 게시글을 수정했습니다.");
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<BaseResponse<Void>> deleteArticle(@PathVariable String articleId, @CurrentUserId String authorId) {
        articleService.deleteArticle(articleId, authorId);

        return BaseResponse.ok("성공적으로 게시글을 삭제했습니다.");
    }
}
