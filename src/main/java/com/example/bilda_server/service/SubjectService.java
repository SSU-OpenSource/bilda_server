package com.example.bilda_server.service;

import com.example.bilda_server.mapper.SubjectMapper;
import com.example.bilda_server.repository.SubjectRepository;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.domain.enums.Department;
import com.example.bilda_server.response.SubjectWithTeamStatusDTO;
import com.example.bilda_server.response.UserSubjectDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserJpaRepository userRepository;
    private final SubjectMapper subjectMapper;

    public List<Subject> findSubjectsByUserDepartment(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Department department = user.getDepartment();
        return subjectRepository.findByDepartmentsContaining(department);
    }

    public List<SubjectWithTeamStatusDTO> findSubjectsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));



        return user.getSubjects().stream()
                .map(subject -> subjectMapper.toSubjectDTO(user,subject))
                .collect(Collectors.toList());
    }

    public UserSubjectDTO addUserToSubject(Long subjectCode, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        Subject subject = subjectRepository.findById(subjectCode)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (user.getSubjects().contains(subject)) {
            throw new IllegalStateException("User already add the Subject");
        }

        user.getSubjects().add(subject);
        userRepository.save(user);

        return subjectMapper.toUserSubjectDTO(user, subject);
    }
}
