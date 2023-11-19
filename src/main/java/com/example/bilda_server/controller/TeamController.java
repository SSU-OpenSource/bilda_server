package com.example.bilda_server.controller;

import com.example.bilda_server.controller.reqeust.CreateTeamRequest;
import com.example.bilda_server.domain.Team;
import com.example.bilda_server.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @ResponseBody
    @GetMapping("teams")
    public String getTeams(){
        return "들어가있는 팀 불러오기 로직";
    }


    @PostMapping("/create/{leaderId}")
    public ResponseEntity<Void> createTeam(
            @PathVariable Long leaderId,
            @RequestBody CreateTeamRequest request) {

        Team team = teamService.createTeam(leaderId, request);
        return ResponseEntity.ok().build();
    }

}
