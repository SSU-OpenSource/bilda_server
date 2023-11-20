package com.example.bilda_server.response;


import com.example.bilda_server.domain.enums.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamsOfSubjectDTO {
    private Long teamId;
    private String teamTitle;
    private String subjectName;
    private RecruitmentStatus recruitmentStatus;
    private Integer memberNum;
    private Integer maxMemberNum;
}
