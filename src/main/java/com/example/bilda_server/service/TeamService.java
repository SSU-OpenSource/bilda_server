package com.example.bilda_server.service;

import com.example.bilda_server.Repository.SubjectRepository;
import com.example.bilda_server.Repository.TeamRepository;
import com.example.bilda_server.Repository.UserJpaRepository;
import com.example.bilda_server.controller.reqeust.CreateTeamRequest;
import com.example.bilda_server.domain.Subject;
import com.example.bilda_server.domain.Team;
import com.example.bilda_server.domain.User;
import com.example.bilda_server.domain.enums.CompleteStatus;
import com.example.bilda_server.domain.enums.RecruitmentStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserJpaRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public Team createTeam(Long leaderId, CreateTeamRequest request) {
        User leader = userRepository.findById(leaderId)
                .orElseThrow(() -> new EntityNotFoundException("Leader not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        List<User> users = new ArrayList<>();
        users.add(leader);

        Team team = Team.builder()
                .leaderId(leaderId)
                .subject(subject)
                .teamTitle(request.getTeamTitle())
                .recruitmentEndDate(request.getRecruitmentEndDate())
                .teamInfoMessage(request.getTeamInfoMessage())
                .recruitmentStatus(RecruitmentStatus.RECRUIT)
                .completeStatus(CompleteStatus.PROGRESS)
                .users(users)
                .pendingUsers(new ArrayList<>())
                .build();

        return teamRepository.save(team);
    }
}
