package com.example.bilda_server.controller;


import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.response.BaseResponse;
import com.example.bilda_server.response.ResponseDto;
import com.example.bilda_server.response.SubjectWithTeamStatusDTO;
import com.example.bilda_server.response.UserSubjectDTO;
import com.example.bilda_server.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bilda_server.utils.RequestURI.SUBJECT_REQUEST_PREFIX;

@Controller
@RequiredArgsConstructor
@RequestMapping(SUBJECT_REQUEST_PREFIX)
public class SubjectController {

    private final SubjectService subjectService;

    @Operation(summary = "유저가 속해있는 학과에 개설된 과목정보 가져오기", description = "userId를 pathVariable로 넘기면 유저가 속해있는 과에 개설된 과목정보를 가져올 수 있습니다.  ", tags = {"SubjectController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Subject[].class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/department/{userId}")
    @ResponseBody
    public ResponseDto<List<Subject>> getSubjectsByDepartment(@PathVariable Long userId) {
        List<Subject> subjects = subjectService.findSubjectsByUserDepartment(userId);
        return ResponseDto.success("과목 정보 조회 완료", subjects);
    }

    @Operation(summary = "유저가 속해있는 과목정보 가져오기", description = "userId를 pathVariable로 넘기면 유저가 듣고있는 과목정보를 가져올 수 있습니다.  ", tags = {"SubjectController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SubjectWithTeamStatusDTO[].class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseDto<List<SubjectWithTeamStatusDTO>> getSubjects(@PathVariable Long userId) {
        List<SubjectWithTeamStatusDTO> subjects = subjectService.findSubjectsByUserId(userId);
        return ResponseDto.success("유저가 속해 있는 과목 정보 조회 완료", subjects);
    }

    @Operation(summary = "유저가 듣고 있는 과목 추가하기", description = "userId와 SubjectId를 pathVariable로 넘기면 유저가 듣고 있는 과목을 추가할 수 있습니다.  ", tags = {"SubjectController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserSubjectDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/{userId}/add/{subjectCode}")
    public ResponseEntity<BaseResponse<UserSubjectDTO>> addSubjectToUser(@PathVariable Long userId, @PathVariable Long subjectCode) {
        UserSubjectDTO userSubjectDTO = subjectService.addUserToSubject(subjectCode, userId);
        return ResponseEntity.ok(new BaseResponse<>(200, "유저의 과목 추가 성공", userSubjectDTO));

    }
}
