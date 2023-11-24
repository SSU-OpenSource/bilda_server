package com.example.bilda_server.mapper;

import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.response.SubjectWithTeamStatusDTO;
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
}
