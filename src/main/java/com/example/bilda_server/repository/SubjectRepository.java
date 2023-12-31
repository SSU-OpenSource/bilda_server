package com.example.bilda_server.repository;

import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.enums.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByDepartmentsContaining(Department department);

}
