package com.example.bilda_server.domain.entity;

import com.example.bilda_server.domain.enums.Department;
import jakarta.persistence.*;
import lombok.*;

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
    private boolean hasTeam; //해당 과목에 팀이 있는지 여부

}
