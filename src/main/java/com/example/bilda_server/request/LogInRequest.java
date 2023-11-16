package com.example.bilda_server.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogInRequest(@Email String email, @NotBlank String password) {

}
