package com.dgsw.environment.controller;

import com.dgsw.environment.dto.request.CreateUserRequest;
import com.dgsw.environment.dto.request.LoginRequest;
import com.dgsw.environment.dto.request.RefreshRequest;
import com.dgsw.environment.dto.response.BaseResponse;
import com.dgsw.environment.dto.response.LoginResponse;
import com.dgsw.environment.dto.response.TokenResponse;
import com.dgsw.environment.dto.response.UserResponse;
import com.dgsw.environment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> signUp(@RequestBody CreateUserRequest request) {
        userService.signUp(request);

        return BaseResponse.created("성공적으로 회원가입을 하였습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return BaseResponse.ok(userService.login(request), "성공적으로 로그인하였습니다.");
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<TokenResponse>> refresh(@RequestBody RefreshRequest request) {
        return BaseResponse.ok(userService.refresh(request), "성공적으로 토큰을 재발급했습니다.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> getUserInfo(@PathVariable String userId) {
        return BaseResponse.ok(userService.getUserInfo(userId), "성공적으로 유저를 조회했습니다.");
    }
}
