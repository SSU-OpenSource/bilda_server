package com.example.bilda_server.controller;


import com.example.bilda_server.request.EvaluationRequestDTO;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.response.TeamMemberEvaluationDTO;
import com.example.bilda_server.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Operation(summary = "평가 생성하기", description = "Http request body를 이용하여 평가를 생성할 수 있습니다. ", tags = {
            "EvaluationController"})
    @PostMapping("/create")
    public ResponseDto<Void> createEvaluation(@RequestBody EvaluationRequestDTO evaluationDTO) {
        evaluationService.createAndReflectEvaluation(evaluationDTO);
        return ResponseDto.success("평가 반영 완료");
    }

    @Operation(summary = "팀원 평가 여부 조회하기", description = "requestParam으로 조회하는 유저의 id를 가져오고 teamId를 pathVariable로 넘기면 팀원들의 평가 여부를 확인할 수 있습니다. ", tags = {
            "EvaluationController"})
    @GetMapping("/status/{teamId}")
    public ResponseEntity<List<TeamMemberEvaluationDTO>> getEvaluationStatus(
            @RequestParam Long userId, @PathVariable Long teamId
    ) {
        List<TeamMemberEvaluationDTO> evaluationStatus = evaluationService.getEvaluationStatusOfTeamMembers(userId, teamId);
        return ResponseEntity.ok(evaluationStatus);
    }


}
