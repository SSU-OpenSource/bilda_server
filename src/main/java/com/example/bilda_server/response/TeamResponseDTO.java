package com.example.bilda_server.response;

import com.example.bilda_server.domain.enums.CompleteStatus;
import com.example.bilda_server.domain.enums.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamResponseDTO {

    private Long teamId;
    private Long leaderId;
    private String teamTitle;
    private String subjectTitle;
    private String leaderName;
    private RecruitmentStatus recruitmentStatus;
    private CompleteStatus completeStatus;
    private LocalDate buildStartDate;
    private String teamInfoMessage;
    private int maxNumber;
    private List<UserResponseDTO> members;
}
