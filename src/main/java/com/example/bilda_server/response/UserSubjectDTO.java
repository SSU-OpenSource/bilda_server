package com.example.bilda_server.response;

import com.example.bilda_server.domain.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSubjectDTO {

    private Long userId;
    private Long subjectCode;
    private String title;
    private String professor;

}
