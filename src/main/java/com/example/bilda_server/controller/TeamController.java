package com.example.bilda_server.controller;

import com.example.bilda_server.controller.reqeust.CreateTeamRequest;
import com.example.bilda_server.controller.response.TeamResponseDTO;
import com.example.bilda_server.domain.Team;
import com.example.bilda_server.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @ResponseBody
    @GetMapping("/{userId}")
    public ResponseEntity<List<TeamResponseDTO>> getTeams(
            @PathVariable Long userId
    ){
        List<TeamResponseDTO> teams = teamService.findTeamsByUserId(userId);
        return ResponseEntity.ok(teams);
    }


    @PostMapping("/create/{leaderId}")
    public ResponseEntity<Void> createTeam(
            @PathVariable Long leaderId,
            @RequestBody CreateTeamRequest request) {

        Team team = teamService.createTeam(leaderId, request);
        return ResponseEntity.ok().build();
    }

}
