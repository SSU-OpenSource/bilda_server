package com.example.bilda_server.domain;

import com.example.bilda_server.domain.enums.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subjectCode;

    private Department department;

    private String professor;
    private LocalDate startDate;
    private LocalDate endDate;
    private String section; //학기 정보
}
