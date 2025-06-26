package com.dgsw.environment.domain.article.controller;

import com.dgsw.environment.domain.article.dto.request.CreateArticleRequest;
import com.dgsw.environment.domain.article.dto.request.UpdateArticleRequest;
import com.dgsw.environment.domain.article.dto.response.ArticleDetailResponse;
import com.dgsw.environment.domain.article.dto.response.ArticleResponse;
import com.dgsw.environment.global.response.BaseResponse;
import com.dgsw.environment.global.annotation.CurrentUserId;
import com.dgsw.environment.domain.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "성공적으로 게시글을 작성하였습니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> writeArticle(@RequestBody CreateArticleRequest request, @CurrentUserId String authorId) {
        articleService.writeArticle(request, authorId);

        return BaseResponse.created("성공적으로 게시글을 작성하였습니다.");
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 게시글 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 조회했습니다.")
    @GetMapping("/{articleId}")
    public ResponseEntity<BaseResponse<ArticleDetailResponse>> getArticle(
            @Parameter(description = "게시글 ID") @PathVariable String articleId
    ) {
        return BaseResponse.ok(articleService.getArticle(articleId), "성공적으로 게시글을 조회했습니다.");
    }

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 게시글 목록을 조회했습니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<List<ArticleResponse>>> getAllArticles() {
        return BaseResponse.ok(articleService.getAllArticles(), "성공적으로 게시글 목록을 조회했습니다.");
    }

    @Operation(summary = "게시글 수정", description = "본인이 작성한 게시글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 수정했습니다.")
    @PutMapping("/{articleId}")
    public ResponseEntity<BaseResponse<Void>> updateArticle(
            @Parameter(description = "게시글 ID") @PathVariable String articleId,
            @RequestBody UpdateArticleRequest request,
            @Parameter(hidden = true) @CurrentUserId String authorId
    ) {
        articleService.updateArticle(articleId, request, authorId);
        return BaseResponse.ok("성공적으로 게시글을 수정했습니다.");
    }

    @Operation(summary = "게시글 삭제", description = "본인이 작성한 게시글을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 삭제했습니다.")
    @DeleteMapping("/{articleId}")
    public ResponseEntity<BaseResponse<Void>> deleteArticle(
            @Parameter(description = "게시글 ID") @PathVariable String articleId,
            @Parameter(hidden = true) @CurrentUserId String authorId
    ) {
        articleService.deleteArticle(articleId, authorId);
        return BaseResponse.ok("성공적으로 게시글을 삭제했습니다.");
    }
}
