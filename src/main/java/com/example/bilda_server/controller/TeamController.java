package com.example.bilda_server.controller;

import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.request.CreateTeamRequest;
import com.example.bilda_server.response.TeamResponseDTO;
import com.example.bilda_server.response.TeamsOfSubjectDTO;
import com.example.bilda_server.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;


    @GetMapping("/{teamId}")
    @ResponseBody
    public ResponseEntity<TeamResponseDTO> getTeam(
            @PathVariable Long teamId
    ) {
        TeamResponseDTO team = teamService.findTeam(teamId);
        return ResponseEntity.ok(team);
    }

    @ResponseBody
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TeamResponseDTO>> getTeams(
            @PathVariable Long userId
    ) {
        List<TeamResponseDTO> teams = teamService.findTeamsByUserId(userId);
        return ResponseEntity.ok(teams);
    }

    @ResponseBody
    @GetMapping("/subject/{subjectId}")

    public ResponseEntity<List<TeamsOfSubjectDTO>> getTeamsBySubjectID(
            @PathVariable Long subjectId
    ) {
        List<TeamsOfSubjectDTO> teams = teamService.findTeamsBySubjectId(subjectId);
        return ResponseEntity.ok(teams);
    }


    @PostMapping("/create/{leaderId}")
    public ResponseEntity<Void> createTeam(
            @PathVariable Long leaderId,
            @RequestBody CreateTeamRequest request) {

        Team team = teamService.createTeam(leaderId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{teamId}/join/{userId}")
    public ResponseEntity<Void> requestJoinTeam(
            @PathVariable Long teamId,
            @PathVariable Long userId
    ){
        teamService.addPendingUserToTeam(teamId, userId);
        return ResponseEntity.ok().build();
    }



}
