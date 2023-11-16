package com.example.bilda_server.config;

import com.example.bilda_server.Repository.SubjectRepository;
import com.example.bilda_server.domain.Subject;
import com.example.bilda_server.domain.enums.Department;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private SubjectRepository subjectRepository;

    @PostConstruct
    public void initData(){

        Set<Department> subject1Dept = new HashSet<>(Arrays.asList(Department.COMPUTER));
        Set<Department> subject2Dept = new HashSet<>(Arrays.asList(Department.COMPUTER, Department.SOFTWARE));

        //과목 데이터 생성
        Subject subject1 = new Subject(101L, "사용자 인터페이스", subject1Dept, "최지웅", "2023 fall");
        Subject subject2 = new Subject(102L, "오픈소스 기초설계", subject2Dept, "최종석", "2023 fall");


        subjectRepository.save(subject1);
        subjectRepository.save(subject2);


    }
}
