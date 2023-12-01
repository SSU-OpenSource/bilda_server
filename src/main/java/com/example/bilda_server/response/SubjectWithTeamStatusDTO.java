package com.example.bilda_server.response;

import com.example.bilda_server.domain.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class SubjectWithTeamStatusDTO {
    private Long SubjectCode;
    private String title;
    private Set<Department> departments;
    private String professor;
    private String section;
    private boolean hasTeam;
}
