package com.example.bilda_server.request;

import jakarta.validation.constraints.Email;

public record VerifyEmailRequest(@Email String email) {

}
