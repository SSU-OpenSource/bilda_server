package com.example.bilda_server.controller;


import com.example.bilda_server.domain.User;
import com.example.bilda_server.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.bilda_server.utils.RequestURI.SUBJECT_REQUEST_PREFIX;

@Controller
@RequestMapping(SUBJECT_REQUEST_PREFIX)
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @GetMapping()
    @ResponseBody
    public String getSubjects(){
        return "subjects";
    }

    @PostMapping("/{userId}/add/{subjectCode}")
    public ResponseEntity<User> addSubjectToUser(@PathVariable Long userId, @PathVariable Long subjectCode){
        subjectService.addUserToSubject(subjectCode, userId);
        return ResponseEntity.ok().build();
    }




}
