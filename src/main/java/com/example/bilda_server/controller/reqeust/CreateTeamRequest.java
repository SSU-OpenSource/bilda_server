package com.example.bilda_server.controller.reqeust;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateTeamRequest {

    private Long subjectId;
    private String teamTitle;
    private LocalDate recruitmentEndDate;
    private String teamInfoMessage;

}
