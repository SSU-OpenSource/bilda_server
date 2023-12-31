package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.request.VerifyAuthCodeRequest;
import com.example.bilda_server.request.VerifyEmailRequest;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(EMAIL_REQUEST_PREFIX)
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "이메일 전송", description = "인증을 위한 이메일을 전송합니다. 인증 코드 입력 후 /verify/code 요청을 보내면 유효한 코드 인지 알 수 있습니다.", tags = {
        "EmailController"})
    @PostMapping("/verify")
    public ResponseDto<Void> verify(@RequestBody VerifyEmailRequest verifyEmailRequest)
        throws RuntimeException {
        try {
            emailService.sendVerificationMail(verifyEmailRequest.email());
            return ResponseDto.success();
        } catch (NotFoundException e) {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "이메일 인증", description = "이메일로부터 전송받은 인증 코드를 인증합니다. result 값이 true면 인증이 완료된 것입니다.", tags = {
        "EmailController"})
    @PostMapping("/verify/code")
    public ResponseDto<Void> getVerify(@RequestBody VerifyAuthCodeRequest verifyAuthCodeRequest)
        throws NotFoundException {
        try {
            emailService.verifyEmail(verifyAuthCodeRequest.email(),
	verifyAuthCodeRequest.authCode());
            return ResponseDto.success();
        } catch (Exception e) {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
