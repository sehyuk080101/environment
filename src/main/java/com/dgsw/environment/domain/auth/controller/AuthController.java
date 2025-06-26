package com.dgsw.environment.domain.auth.controller;

import com.dgsw.environment.domain.auth.dto.request.LoginRequest;
import com.dgsw.environment.domain.auth.dto.request.RefreshRequest;
import com.dgsw.environment.domain.auth.dto.response.LoginResponse;
import com.dgsw.environment.domain.auth.dto.response.TokenResponse;
import com.dgsw.environment.domain.auth.service.AuthService;
import com.dgsw.environment.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return BaseResponse.ok(authService.login(request), "성공적으로 로그인하였습니다.");
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 사용하여 액세스 토큰을 재발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "401", description = "리프레시 토큰 오류")
    })
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<TokenResponse>> refresh(@RequestBody RefreshRequest request) {
        return BaseResponse.ok(authService.refresh(request), "성공적으로 토큰을 재발급했습니다.");
    }
}
