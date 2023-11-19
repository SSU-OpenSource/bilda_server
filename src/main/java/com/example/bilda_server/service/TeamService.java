package com.example.bilda_server.service;

import com.example.bilda_server.Repository.SubjectRepository;
import com.example.bilda_server.Repository.TeamRepository;
import com.example.bilda_server.Repository.UserJpaRepository;
import com.example.bilda_server.controller.reqeust.CreateTeamRequest;
import com.example.bilda_server.controller.response.TeamResponseDTO;
import com.example.bilda_server.controller.response.UserResponseDTO;
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
import java.util.stream.Collectors;

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
                .leader(leader)
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

    public TeamResponseDTO findTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("team not found"));
        return convertToTeamDTO(team);
    }


    public List<TeamResponseDTO> findTeamsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        return user.getTeams().stream()
                .map(this::convertToTeamDTO)
                .collect(Collectors.toList());

    }

    private TeamResponseDTO convertToTeamDTO(Team team) {

        List<UserResponseDTO> memberDTOs = team.getUsers().stream()
                .map(user -> new UserResponseDTO(user.getUserId(), user.getName()))
                .toList();

        return new TeamResponseDTO(
                team.getTeamId(),
                team.getTeamTitle(),
                team.getSubject().getTitle(),
                team.getLeader().getName(),
                team.getRecruitmentStatus(),
                team.getBuildStartDate(),
                memberDTOs
        );
    }
}
