package com.example.bilda_server.service;

import com.example.bilda_server.repository.SubjectRepository;
import com.example.bilda_server.repository.UserRepository;
import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.domain.enums.Department;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public List<Subject> findSubjectsByUserDepartment(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Department department = user.getDepartment();
        return subjectRepository.findByDepartmentsContaining(department);
    }

    public List<Subject> findSubjectsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return user.getSubjects();
    }

    public void addUserToSubject(Long subjectCode, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        Subject subject = subjectRepository.findById(subjectCode)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (user.getSubjects().contains(subject)) {
            throw new IllegalStateException("User already add the Subject");
        }

        user.getSubjects().add(subject);
        userRepository.save(user);
    }
}
