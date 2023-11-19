package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.request.ChangePasswordRequest;
import com.example.bilda_server.request.SignupRequest;
import com.example.bilda_server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(USER_REQUEST_PREFIX)
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.signup(signupRequest));
    }

    @PutMapping("/password")
    public ResponseEntity<User> changePassword(
        @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(userService.changePassword(changePasswordRequest, userDetails));
    }
}
