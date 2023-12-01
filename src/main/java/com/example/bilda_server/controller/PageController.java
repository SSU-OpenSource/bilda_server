package com.example.bilda_server.controller;


import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.response.PageAverageDTO;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.response.TeamMemberEvaluationDTO;
import com.example.bilda_server.service.PageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/page")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @Operation(summary = "마이페이지 조회", description = "마이페이지의 정보들을 조회할 수 있습니다. ", tags = {
            "PageController"})
    @GetMapping("")
    public ResponseDto<PageAverageDTO> getPageByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            PageAverageDTO pageAverage = pageService.getAverageScores(userDetails.getId());
            return ResponseDto.success("마이 페이지 조회", pageAverage);
        }catch (Exception ex) {
            return ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류");
        }

    }

    @Operation(summary = "팀원의 마이페이지 조회", description = "조회하고자하는 user의 id를 pathVariable로 넘기면 마이페이지의 정보들을 조회할 수 있습니다. ", tags = {
            "PageController"})
    @GetMapping("/{userId}")
    public ResponseDto<PageAverageDTO> getPageByUserId(@PathVariable Long userId) {
        try{
            PageAverageDTO pageAverage = pageService.getAverageScores(userId);
            return ResponseDto.success("마이 페이지 조회", pageAverage);
        }catch (Exception ex) {
            return ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류");
        }
    }


}
