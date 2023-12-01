package com.example.bilda_server.config;

import com.example.bilda_server.domain.entity.Page;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.domain.enums.Role;
import com.example.bilda_server.repository.SubjectRepository;
import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.enums.Department;
import com.example.bilda_server.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initData() {

        Set<Department> subject1Dept = new HashSet<>(Arrays.asList(Department.COMPUTER));
        Set<Department> subject2Dept = new HashSet<>(
            Arrays.asList(Department.COMPUTER, Department.SOFTWARE));

        //과목 데이터 생성
        Subject subject1 = new Subject(101L, "사용자 인터페이스", subject1Dept, "최지웅", "2023 fall", false);
        Subject subject2 = new Subject(102L, "오픈소스 기초설계", subject2Dept, "최종석", "2023 fall", false);

        subjectRepository.save(subject1);
        subjectRepository.save(subject2);

        User user1 = new User(1L, "happy@gmail.com", "super1234!", "홍길동", "gildong", "20230001",
            Department.COMPUTER, new Page(), null, null, Role.USER);
        user1.getMyPage().setUser(user1);
        User user2 = new User(2L, "test@gmail.com", "super1234!", "테스트", "test", "20230002",
            Department.COMPUTER, new Page(), null, null, Role.USER);
        user2.getMyPage().setUser(user2);

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
