package com.example.bilda_server.controller;


import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.response.PageAverageDTO;
import com.example.bilda_server.response.TeamMemberEvaluationDTO;
import com.example.bilda_server.service.PageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
    public PageAverageDTO getPageByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return pageService.getAverageScores(userDetails.getId());
    }

    @Operation(summary = "팀원의 마이페이지 조회", description = "조회하고자하는 user의 id를 pathVariable로 넘기면 마이페이지의 정보들을 조회할 수 있습니다. ", tags = {
            "PageController"})
    @GetMapping("/{userId}")
    public PageAverageDTO getPageByUserId(@PathVariable Long userId) {
        return pageService.getAverageScores(userId);
    }


}
