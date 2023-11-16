package com.example.bilda_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Subject {
    @Id
    private Long subjectCode;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String professor;
    private String section; //학기 정보
}
