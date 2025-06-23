package com.dgsw.environment.controller;

import com.dgsw.environment.dto.*;
import com.dgsw.environment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDTO signUp(@RequestBody CreateUserRequest request) {
        userService.signUp(request);

        return new ResponseDTO("성공적으로 회원가입을 하였습니다.");
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody RefreshRequest request) {
        return userService.refresh(request);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserInfo(@PathVariable String userId) {
        return userService.getUserInfo(userId);
    }
}
