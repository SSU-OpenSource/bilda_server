package com.example.bilda_server.controller;

import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.request.CreateTeamRequest;
import com.example.bilda_server.response.*;
import com.example.bilda_server.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "팀의 정보 가져오기", description = "teamId를 pathVariable로 넘기면 해당 팀에 대한 정보가 나옵니다. ", tags = {"TeamController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = TeamResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{teamId}")
    @ResponseBody
    public ResponseDto<TeamResponseDTO> getTeam(@PathVariable Long teamId) {
        try {
            TeamResponseDTO team = teamService.findTeam(teamId);
            return ResponseDto.success("팀 정보 조회 완료", team);
        } catch (EntityNotFoundException ex) {
            return ResponseDto.fail(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = TeamResponseDTO[].class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "USER NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @Operation(summary = "User가 속해있는 팀들의 정보를 가져옵니다.", description = "userId를 pathVariable로 넘기면 해당 팀들에 대한 정보가 나옵니다. ", tags = {"TeamController"})
    @ResponseBody
    @GetMapping("/user")
    public ResponseDto<List<TeamResponseDTO>> getTeams(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try{
            List<TeamResponseDTO> teams = teamService.findTeamsByUserId(userDetails.getId());
            return ResponseDto.success("유저가 속해있는 팀 정보 조회 완료", teams);
        }catch (EntityNotFoundException ex) {
            return ResponseDto.fail(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "과목에 해당하는 팀들의 정보 가져오기", description = "SubjectId를 pahtvariable로 넘기면 해당 과목에 대해 개설된 팀들의 정보가 나옵니다. recruitmentStatus로 분기 처리해주시면 됩니다. ", tags = {"TeamController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teams of subject retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TeamsOfSubjectDTO[].class))),
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @ResponseBody
    @GetMapping("/subject/{subjectId}")
    public ResponseDto<List<TeamsOfSubjectDTO>> getTeamsBySubjectID(
        @PathVariable Long subjectId
    ) {
        try{
            List<TeamsOfSubjectDTO> teams = teamService.findTeamsBySubjectId(subjectId);
            return ResponseDto.success("과목에 해당하는 팀 정보 조회 완료", teams);
        }catch (EntityNotFoundException ex) {
            return ResponseDto.fail(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }


    @Operation(summary = "팀 생성하기", description = "Http request body를 이용하여 리더가 팀을 생성할 수 있습니다. ", tags = {
        "TeamController"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Team created successfully",
                    content = @Content(schema = @Schema(implementation = TeamResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Leader or Subject not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createTeam(
        @RequestBody CreateTeamRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            TeamResponseDTO team = teamService.createTeam(userDetails.getId(), request);
            return ResponseEntity.ok(new BaseResponse<>(200, "팀 생성", team));
        }catch (EntityNotFoundException ex) {
            BaseResponse<String> errorResponse = new BaseResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "팀 조인 요청 수락하기", description = "TeamId와 팀에 추가할 userId를 pathVariable로 넘기면 leader가 join요청을 수락할 수 있습니다. userId는 팀 조인 요청을 확인하는 api를 통해 가져와 주세요 조인을 수락했을 때 해당 팀의 인원수가 초기 설정한 max인원수와 같아지면 team의 모집 상태는 모집 완료로 바뀌게 됩니다.", tags = {
        "TeamController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Join request approved successfully"),
            @ApiResponse(responseCode = "404", description = "Team or pending user not found"),
            @ApiResponse(responseCode = "400", description = "No pending request from the user to this team"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/{teamId}/approve/{pendingUserId}")
    public ResponseEntity<BaseResponse<Void>> approveJoinRequest(
            @PathVariable Long teamId,
            @PathVariable Long pendingUserId) {
        try {
            teamService.approvePendingUser(teamId, pendingUserId);
            return ResponseEntity.ok(new BaseResponse<>(200, "팀 조인 요청 수락", null));
        } catch (EntityNotFoundException ex) {
            // EntityNotFoundException 발생 시
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException ex) {
            // IllegalStateException 발생 시
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // 그 외 일반 예외 발생 시
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "팀 조인 요청 거절하기", description = "TeamId와 팀에 추가할 userId를 pathVariable로 넘기면 leader가 join요청을 거절할 수 있습니다. ", tags = {
        "TeamController"})
    @PostMapping("{teamId}/reject/{pendingUserId}")
    public ResponseDto<Void> rejectJoinRequest(
        @PathVariable Long teamId,
        @PathVariable Long pendingUserId
    ) {
        try{
            teamService.rejectPendingUser(teamId, pendingUserId);
            return ResponseDto.success( "팀 조인 요청 거절", null);
        }catch (EntityNotFoundException ex) {
            // EntityNotFoundException 발생 시
            return ResponseDto.fail(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (IllegalStateException ex) {
            // IllegalStateException 발생 시
            return ResponseDto.fail(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            // 그 외 일반 예외 발생 시
            return ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류");
        }

    }


    @Operation(summary = "팀에 조인 요청하기", description = "TeamId와 팀에 추가할 userId를 pathVariable로 넘기면 해당 팀에 사용자가 조인 요청을 할 수 있습니다. ", tags = {
        "TeamController"})
    @PostMapping("/{teamId}/join")
    public ResponseEntity<BaseResponse<Void>> requestJoinTeam(
        @PathVariable Long teamId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try{
            teamService.addPendingUserToTeam(teamId, userDetails.getId());
            return ResponseEntity.ok(new BaseResponse<>(200, "팀 조인 요청 완료", null));
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }catch (IllegalStateException ex) {
            // IllegalStateException 발생 시
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // 그 외 일반 예외 발생 시
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "팀 조인 요청 확인하기", description = "TeamId를 pathVariable로 넘기면 해당 팀에 조인 요청을 보낸 사용자들이 불러와집니다 ", tags = {
        "TeamController"})
    @GetMapping("/{teamId}/recruit")
    public ResponseEntity<BaseResponse<List<PendingUserDTO>>> getPendingUsersByTeamId(
        @PathVariable Long teamId
    ) {
        try{
            List<PendingUserDTO> pendingUsers = teamService.getPendingUsers(teamId);
            return ResponseEntity.ok(new BaseResponse<>(200, "팀 조인 요청 확인", pendingUsers));
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "팀플 종료하기", description = "TeamId를 pathVariable로 넘기면 팀의 completeStatus는 COMPLETE로 바뀌게 됩니다. ", tags = {
        "TeamController"})
    @PostMapping("/complete/{teamId}")
    public ResponseDto<Void> completeTeam(
        @PathVariable Long teamId
    ) {
        try{
            teamService.setCompleteStatus(teamId);
            return ResponseDto.success();
        }catch (EntityNotFoundException ex) {
            return ResponseDto.fail(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "종료된 팀 불러오기", description = "user가 가지고 있는 팀들중 팀플이 완료된 팀들이 불러와집니다.  ", tags = {"TeamController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Completed teams retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/completed-teams")
    public ResponseEntity<?> getCompletedTeams(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            List<TeamResponseDTO> completedTeams = teamService.findCompletedTeam(userDetails.getId());
            return ResponseEntity.ok(completedTeams);
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }


    }

}
