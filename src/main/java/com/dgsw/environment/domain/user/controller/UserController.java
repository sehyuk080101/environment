package com.dgsw.environment.domain.user.controller;

import com.dgsw.environment.domain.user.dto.request.CreateUserRequest;
import com.dgsw.environment.domain.user.dto.request.UpdatePasswordRequest;
import com.dgsw.environment.domain.user.dto.request.UpdateUsernameRequest;
import com.dgsw.environment.global.annotation.CurrentUserId;
import com.dgsw.environment.global.response.BaseResponse;
import com.dgsw.environment.domain.user.dto.response.UserResponse;
import com.dgsw.environment.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "사용자가 회원가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> signUp(@RequestBody CreateUserRequest request) {
        userService.signUp(request);
        return BaseResponse.created("성공적으로 회원가입을 하였습니다.");
    }

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> getUserInfo(
            @Parameter(hidden = true) @CurrentUserId String userId) {
        return BaseResponse.ok(userService.getUserInfo(userId), "성공적으로 유저를 조회했습니다.");
    }

    @Operation(summary = "이름 변경", description = "로그인한 사용자의 이름을 변경합니다.")
    @PutMapping("/me/username")
    public ResponseEntity<BaseResponse<Void>> updateUsername(
            @RequestBody UpdateUsernameRequest request,
            @Parameter(hidden = true) @CurrentUserId String userId) {
        userService.updateUsername(request, userId);
        return BaseResponse.ok("성공적으로 이름을 변경했습니다.");
    }

    @Operation(summary = "비밀번호 변경", description = "로그인한 사용자의 비밀번호를 변경합니다.")
    @PutMapping("/me/password")
    public ResponseEntity<BaseResponse<Void>> updatePassword(
            @RequestBody UpdatePasswordRequest request,
            @Parameter(hidden = true) @CurrentUserId String userId) {
        userService.updatePassword(request, userId);
        return BaseResponse.ok("성공적으로 비밀번호를 변경했습니다.");
    }
}
