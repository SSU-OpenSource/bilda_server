package com.example.bilda_server.controller;


import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.request.EvaluationRequestDTO;
import com.example.bilda_server.response.BaseResponse;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.response.TeamMemberEvaluationDTO;
import com.example.bilda_server.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseDto<Void> createEvaluation(
            @RequestBody EvaluationRequestDTO evaluationDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try{
            evaluationService.createAndReflectEvaluation(evaluationDTO, userDetails.getId());
            return ResponseDto.success("평가 반영 완료");
        }catch (EntityNotFoundException ex) {
            // EntityNotFoundException 발생 시
            return ResponseDto.fail(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            // 그 외 일반 예외 발생 시
            return ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류");
        }

    }

    @Operation(summary = "팀원 평가 여부 조회하기", description = "requestParam으로 조회하는 유저의 id를 가져오고 teamId를 pathVariable로 넘기면 팀원들의 평가 여부를 확인할 수 있습니다. ", tags = {
            "EvaluationController"})
    @GetMapping("/status/{teamId}")
    public ResponseEntity<?> getEvaluationStatus(
            @PathVariable Long teamId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try{
            List<TeamMemberEvaluationDTO> evaluationStatus = evaluationService.getEvaluationStatusOfTeamMembers(userDetails.getId(), teamId);
            return ResponseEntity.ok(evaluationStatus);
        }catch (EntityNotFoundException ex) {
            // EntityNotFoundException 발생 시
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            // 그 외 일반 예외 발생 시
            return new ResponseEntity<>(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
