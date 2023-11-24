package com.example.bilda_server.controller;

import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.request.CreateTeamRequest;
import com.example.bilda_server.response.PendingUserDTO;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.response.TeamResponseDTO;
import com.example.bilda_server.response.TeamsOfSubjectDTO;
import com.example.bilda_server.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "팀의 정보 가져오기", description = "teamId를 pathVariable로 넘기면 해당 팀에 대한 정보가 나옵니다. ", tags = {
        "TeamController"})
    @GetMapping("/{teamId}")
    @ResponseBody
    public ResponseDto<TeamResponseDTO> getTeam(
        @PathVariable Long teamId
    ) {

        TeamResponseDTO team = teamService.findTeam(teamId);
        return ResponseDto.success("팀 정보 조회 안료", team);
    }

    @Operation(summary = "User가 속해있는 팀들의 정보를 가져옵니다.", description = "userId를 pathVariable로 넘기면 해당 팀들에 대한 정보가 나옵니다. ", tags = {
        "TeamController"})
    @ResponseBody
    @GetMapping("/user/{userId}")
    public ResponseDto<List<TeamResponseDTO>> getTeams(
        @PathVariable Long userId
    ) {
        List<TeamResponseDTO> teams = teamService.findTeamsByUserId(userId);
        return ResponseDto.success("유저가 속해있는 팀 정보 조회 완료", teams);
    }

    @Operation(summary = "과목에 해당하는 팀들의 정보 가져오기", description = "SubjectId를 pahtvariable로 넘기면 해당 과목에 대해 개설된 팀들의 정보가 나옵니다. recruitmentStatus로 분기 처리해주시면 됩니다. ", tags = {
        "TeamController"})
    @ResponseBody
    @GetMapping("/subject/{subjectId}")
    public ResponseDto<List<TeamsOfSubjectDTO>> getTeamsBySubjectID(
        @PathVariable Long subjectId
    ) {
        List<TeamsOfSubjectDTO> teams = teamService.findTeamsBySubjectId(subjectId);
        return ResponseDto.success("과목에 해당하는 팀 정보 조회 완료", teams);
    }

    @Operation(summary = "팀 생성하기", description = "Http request body를 이용하여 리더가 팀을 생성할 수 있습니다. ", tags = {
        "TeamController"})
    @PostMapping("/create/{leaderId}")
    public ResponseDto<Void> createTeam(
        @PathVariable Long leaderId,
        @RequestBody CreateTeamRequest request) {

        Team team = teamService.createTeam(leaderId, request);
        return ResponseDto.success();
    }

    @Operation(summary = "팀 조인 요청 수락하기", description = "TeamId와 팀에 추가할 userId를 pathVariable로 넘기면 leader가 join요청을 수락할 수 있습니다. 조인을 수락했을 때 해당 팀의 인원수가 초기 설정한 max인원수와 같아지면 team의 모집 상태는 모집 완료로 바뀌게 됩니다.", tags = {
        "TeamController"})
    @PostMapping("{teamId}/approve/{pendingUserId}")
    public ResponseDto<Void> approveJoinRequest(
        @PathVariable Long teamId,
        @PathVariable Long pendingUserId
    ) {
        teamService.approvePendingUser(teamId, pendingUserId);
        return ResponseDto.success();
    }

    @Operation(summary = "팀 조인 요청 거절하기", description = "TeamId와 팀에 추가할 userId를 pathVariable로 넘기면 leader가 join요청을 거절할 수 있습니다. ", tags = {
        "TeamController"})
    @PostMapping("{teamId}/reject/{pendingUserId}")
    public ResponseDto<Void> rejectJoinRequest(
        @PathVariable Long teamId,
        @PathVariable Long pendingUserId
    ) {
        teamService.rejectPendingUser(teamId, pendingUserId);
        return ResponseDto.success();
    }


    @Operation(summary = "팀에 조인 요청하기", description = "TeamId와 팀에 추가할 userId를 pathVariable로 넘기면 해당 팀에 사용자가 조인 요청을 할 수 있습니다. ", tags = {
        "TeamController"})
    @PostMapping("/{teamId}/join/{userId}")
    public ResponseDto<Void> requestJoinTeam(
        @PathVariable Long teamId,
        @PathVariable Long userId
    ) {
        teamService.addPendingUserToTeam(teamId, userId);
        return ResponseDto.success();
    }

    @Operation(summary = "팀 조인 요청 확인하기", description = "TeamId를 pathVariable로 넘기면 해당 팀에 조인 요청을 보낸 사용자들이 불러와집니다 ", tags = {
        "TeamController"})
    @GetMapping("/{teamId}/recruit")
    public ResponseDto<List<PendingUserDTO>> getPendingUsersByTeamId(
        @PathVariable Long teamId
    ) {

        List<PendingUserDTO> pendingUsers = teamService.getPendingUsers(teamId);
        return ResponseDto.success("팀 종인 요청 확인", pendingUsers);
    }

    @Operation(summary = "팀플 종료하기", description = "TeamId를 pathVariable로 넘기면 팀의 completeStatus는 COMPLETE로 바뀌게 됩니다. ", tags = {
        "TeamController"})
    @PostMapping("/complete/{teamId}")
    public ResponseDto<Void> completeTeam(
        @PathVariable Long teamId
    ) {
        teamService.setCompleteStatus(teamId);
        return ResponseDto.success();
    }
}
