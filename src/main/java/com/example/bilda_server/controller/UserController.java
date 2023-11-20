package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.request.ChangeNicknameRequest;
import com.example.bilda_server.request.ChangePasswordRequest;
import com.example.bilda_server.request.SignupRequest;
import com.example.bilda_server.response.ChangeNicknameResponse;
import com.example.bilda_server.response.ChangePasswordResponse;
import com.example.bilda_server.response.SignupResponse;
import com.example.bilda_server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Operation(summary = "회원 가입 요청", description = "HTTP Body를 토대로 회원 가입을 진행합니다.", tags = {
        "UserController"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = SignupResponse.class))),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.signup(signupRequest));
    }

    @Operation(summary = "비밀 번호 변경 요청", description = "변경할 비밀번호를 HTTP Body에 담아 보내면 비밀번호가 변경됩니다.", tags = {
        "UserController"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = ChangePasswordResponse.class))),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
        @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(userService.changePassword(changePasswordRequest, userDetails));
    }

    @Operation(summary = "닉네임 변경 요청", description = "변경할 닉네임을 HTTP Body에 담아 보내면 닉네임이 변경됩니다.", tags = {
        "UserController"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = ChangeNicknameResponse.class))),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/nickname")
    public ResponseEntity<ChangeNicknameResponse> changeNickname(
        @RequestBody @Valid ChangeNicknameRequest changeNicknameRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(userService.changeNickname(changeNicknameRequest, userDetails));
    }

}
