package com.example.bilda_server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamMemberEvaluationDTO {
    private Long userId;
    private String memberName;
    private boolean hasEvaluated;
}
