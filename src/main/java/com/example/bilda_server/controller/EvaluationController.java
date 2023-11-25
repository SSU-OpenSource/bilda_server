package com.example.bilda_server.controller;


import com.example.bilda_server.request.EvaluationRequestDTO;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
