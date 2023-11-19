package com.example.bilda_server.service;

import com.example.bilda_server.repository.SubjectRepository;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.domain.Subject;
import com.example.bilda_server.domain.User;
import com.example.bilda_server.domain.enums.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserJpaRepository userRepository;

    public List<Subject> findSubjectsByUserDepartment(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Department department = user.getDepartment();
        return subjectRepository.findByDepartmentsContaining(department);
    }

    public void addUserToSubject(Long subjectCode, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        Subject subject = subjectRepository.findById(subjectCode)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        user.getSubjects().add(subject);
        userRepository.save(user);
    }
}
