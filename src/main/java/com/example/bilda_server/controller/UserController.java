package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.domain.User;
import com.example.bilda_server.request.LogInRequest;
import com.example.bilda_server.request.UserSignUpRequest;
import com.example.bilda_server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(USER_REQUEST_PREFIX)
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        return ResponseEntity.ok(userService.signUp(userSignUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<User> logIn(@RequestBody @Valid LogInRequest logInRequest) {
        return ResponseEntity.ok(userService.logIn(logInRequest));
    }
}
