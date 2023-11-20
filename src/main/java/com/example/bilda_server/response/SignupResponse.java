package com.example.bilda_server.response;

import com.example.bilda_server.domain.enums.Department;

public record SignupResponse(String email,
	             String password,
	             String name,
	             String studentId,
	             String nickname,
	             Department department
) {

}
