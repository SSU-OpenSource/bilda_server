package com.example.bilda_server.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank String password) {

}
