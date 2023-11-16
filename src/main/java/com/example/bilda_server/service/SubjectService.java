package com.example.bilda_server.service;

import com.example.bilda_server.Repository.SubjectRepository;
import com.example.bilda_server.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
}
