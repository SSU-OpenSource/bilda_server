package com.example.bilda_server.controller;

import static com.example.bilda_server.utils.RequestURI.*;

import com.example.bilda_server.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(EMAIL_REQUEST_PREFIX)
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/verify")
    public ResponseEntity verify(@RequestParam String email) throws RuntimeException {
        try {
            emailService.sendVerificationMail(email);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/verify/code")
    public ResponseEntity getVerify(@RequestParam String email, @RequestParam String authCode)
        throws NotFoundException {
        try {
            emailService.verifyEmail(email, authCode);
            HttpHeaders httpHeaders = new HttpHeaders();
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
