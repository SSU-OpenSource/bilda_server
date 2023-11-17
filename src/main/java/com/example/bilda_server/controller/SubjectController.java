package com.example.bilda_server.controller;


import com.example.bilda_server.domain.Subject;
import com.example.bilda_server.domain.User;
import com.example.bilda_server.domain.enums.Department;
import com.example.bilda_server.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bilda_server.utils.RequestURI.SUBJECT_REQUEST_PREFIX;

@Controller
@RequestMapping(SUBJECT_REQUEST_PREFIX)
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @GetMapping("/{department}")
    @ResponseBody
    public ResponseEntity<List<Subject>> getSubjectsByDepartment(@PathVariable Department department) {
        List<Subject> subjects = subjectService.findSubjectsByDepartment(department);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping("/{userId}/add/{subjectCode}")
    public ResponseEntity<User> addSubjectToUser(@PathVariable Long userId, @PathVariable Long subjectCode){
        subjectService.addUserToSubject(subjectCode, userId);
        return ResponseEntity.ok().build();
    }




}
