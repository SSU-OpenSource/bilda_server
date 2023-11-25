package com.example.bilda_server.request;

import jakarta.validation.constraints.Email;

public record VerifyAuthCodeRequest(@Email String email, String authCode) {

}
