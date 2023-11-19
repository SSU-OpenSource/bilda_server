package com.example.bilda_server.controller;


import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.service.SubjectService;
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

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<List<Subject>> getSubjectsByDepartment(@PathVariable Long userId) {
        List<Subject> subjects = subjectService.findSubjectsByUserDepartment(userId);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping("/{userId}/add/{subjectCode}")
    public ResponseEntity<User> addSubjectToUser(@PathVariable Long userId, @PathVariable Long subjectCode){
        subjectService.addUserToSubject(subjectCode, userId);
        return ResponseEntity.ok().build();
    }




}
