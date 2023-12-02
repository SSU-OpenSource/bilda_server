package com.example.bilda_server.response;

import com.example.bilda_server.domain.enums.Department;
import lombok.Builder;

public record SignInResponse(String name, String nickname, String studentId,
	             Department department) {

}
