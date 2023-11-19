package com.example.bilda_server.controller.response;

import com.example.bilda_server.domain.enums.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

//사용자가 해당한 팀들을 불러올 때는 팀 아이디, 팀 제목, 과목 명만을 받아온다.
//
@Getter
@AllArgsConstructor
public class TeamResponseDTO {
    private Long teamId;
    private String teamTitle;
    private String subjectTitle;
    private String leaderName;
    private RecruitmentStatus recruitmentStatus;
    private LocalDate buildStartDate;
    private List<UserResponseDTO> members;

}
