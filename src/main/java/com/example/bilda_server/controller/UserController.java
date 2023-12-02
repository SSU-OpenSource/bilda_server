package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.request.ChangeNicknameRequest;
import com.example.bilda_server.request.ChangePasswordRequest;
import com.example.bilda_server.request.SignInRequest;
import com.example.bilda_server.request.SignupRequest;
import com.example.bilda_server.response.ChangeNicknameResponse;
import com.example.bilda_server.response.ChangePasswordResponse;
import com.example.bilda_server.response.AuthorizedResponse;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.response.SignupResponse;
import com.example.bilda_server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_REQUEST_PREFIX)
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인 요청", description = "로그인을 진행합니다.", tags = {
        "UserController"})
    @PostMapping("/signin")
    public ResponseDto<AuthorizedResponse> signin(@RequestBody @Valid SignInRequest signinRequest) {
        return ResponseDto.success("로그인 성공", userService.signin(signinRequest));
    }

    @Operation(summary = "회원 가입 요청", description = "HTTP Body를 토대로 회원 가입을 진행합니다.", tags = {
        "UserController"})
    @PostMapping("/signup")
    public ResponseDto<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseDto.success("회원 가입 성공", userService.signup(signupRequest));
    }

    @Operation(summary = "비밀 번호 변경 요청", description = "변경할 비밀번호를 HTTP Body에 담아 보내면 비밀번호가 변경됩니다.", tags = {
        "UserController"})
    @PutMapping("/password")
    public ResponseDto<ChangePasswordResponse> changePassword(
        @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseDto.success("비밀번호 변경 완료",
            userService.changePassword(changePasswordRequest, userDetails));
    }

    @Operation(summary = "닉네임 변경 요청", description = "변경할 닉네임을 HTTP Body에 담아 보내면 닉네임이 변경됩니다.", tags = {
        "UserController"})
    @PutMapping("/nickname")
    public ResponseDto<ChangeNicknameResponse> changeNickname(
        @RequestBody @Valid ChangeNicknameRequest changeNicknameRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseDto.success("닉네임 변경 완료",
            userService.changeNickname(changeNicknameRequest, userDetails));
    }

    @Operation(summary = "약관 호출", description = "호출 시 약관이 반환됩니다.", tags = {"UserController"})
    @GetMapping("/terms")
    public ResponseDto<String> getTerms() {
        return ResponseDto.success("약관 반환 완료", userService.getTerms());
    }
}
