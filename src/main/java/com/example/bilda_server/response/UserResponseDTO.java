package com.example.bilda_server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String nickName;

}
