package com.example.bilda_server.response;

import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.domain.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PendingUserDTO {
    private Long userId;
    private String userName;
    private Department department;
}
