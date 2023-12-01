package com.example.bilda_server.mapper;

import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.response.SubjectWithTeamStatusDTO;
import com.example.bilda_server.response.UserSubjectDTO;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public SubjectWithTeamStatusDTO toSubjectDTO(User user, Subject subject) {
        boolean hasTeam = user.getTeams().stream()
                .anyMatch(team -> team.getSubject().equals(subject));

        return new SubjectWithTeamStatusDTO(
                subject.getSubjectCode(),
                subject.getTitle(),
                subject.getDepartments(),
                subject.getProfessor(),
                subject.getSection(),
                hasTeam
        );
    }

    public UserSubjectDTO toUserSubjectDTO(User user, Subject subject) {
        return new UserSubjectDTO(
                user.getId(),
                subject.getSubjectCode(),
                subject.getTitle(),
                subject.getProfessor()
        );
    }
}
