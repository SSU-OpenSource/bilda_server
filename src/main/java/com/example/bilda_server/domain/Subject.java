package com.example.bilda_server.domain;

import com.example.bilda_server.domain.enums.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
