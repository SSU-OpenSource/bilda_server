package com.example.bilda_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    private Long subjectCode;

    private String title;

    @ElementCollection(targetClass = Department.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "subject_department", joinColumns = @JoinColumn(name = "subjectCode"))
    @Column(name = "department")
    private Set<Department> departments;

    private String professor;
    private String section; //학기 정보

}
