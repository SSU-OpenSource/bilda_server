package com.example.bilda_server.request;

import com.example.bilda_server.domain.enums.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignUpRequest(
    @NotBlank
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    String email,

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^[A-Z][A-Za-z0-9]*")
    String password,
    @NotBlank
    String name,
    @NotBlank
    String studentId,
    @NotEmpty
    String nickname,
    Department department
) {

}

