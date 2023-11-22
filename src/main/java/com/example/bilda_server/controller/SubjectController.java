package com.example.bilda_server.controller;


import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping("/department/{userId}")
    @ResponseBody
    public ResponseEntity<List<Subject>> getSubjectsByDepartment(@PathVariable Long userId) {
        List<Subject> subjects = subjectService.findSubjectsByUserDepartment(userId);
        return ResponseEntity.ok(subjects);
    }

    @Operation(summary = "유저가 속해있는 과목정보 가져오기", description = "userId를 pathVariable로 넘기면 유저가 듣고있는 과목정보를 가져올 수 있습니다.  ", tags = {"SubjectController"})
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<List<Subject>> getSubjects(@PathVariable Long userId) {
        List<Subject> subjects = subjectService.findSubjectsByUserId(userId);
        return ResponseEntity.ok(subjects);
    }

    @Operation(summary = "유저가 듣고 있는 과목 추가하기", description = "userId와 SubjectId를 pathVariable로 넘기면 유저가 듣고 있는 과목을 추가할 수 있습니다.  ", tags = {"SubjectController"})
    @PostMapping("/{userId}/add/{subjectCode}")
    public ResponseEntity<User> addSubjectToUser(@PathVariable Long userId, @PathVariable Long subjectCode) {
        subjectService.addUserToSubject(subjectCode, userId);
        return ResponseEntity.ok().build();
    }


}
