package com.example.bilda_server.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeNicknameRequest(@NotBlank String nickname) {

}
