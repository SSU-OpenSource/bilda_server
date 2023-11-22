package com.example.bilda_server.service;

import com.example.bilda_server.repository.SubjectRepository;
import com.example.bilda_server.repository.TeamRepository;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.domain.enums.CompleteStatus;
import com.example.bilda_server.domain.enums.RecruitmentStatus;
import com.example.bilda_server.request.CreateTeamRequest;
import com.example.bilda_server.response.TeamResponseDTO;
import com.example.bilda_server.response.TeamsOfSubjectDTO;
import com.example.bilda_server.response.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .maxMemberNum(request.getMaxMember())
                .teamInfoMessage(request.getTeamInfoMessage())
                .recruitmentStatus(RecruitmentStatus.RECRUIT)
                .completeStatus(CompleteStatus.PROGRESS)
                .users(users)
                .pendingUsers(new ArrayList<>())
                .build();
        teamRepository.save(team);

        return team;
    }

    public TeamResponseDTO findTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("team not found"));
        return convertToTeamDTO(team);
    }

    public List<TeamsOfSubjectDTO> findTeamsBySubjectId(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("subject not found"));

        List<Team> teams = teamRepository.findBySubject(
                subject
        );

        return teams.stream()
                .map(this::convertToTeamsOfSubjectDTO)
                .collect(Collectors.toList());
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
                .map(user -> new UserResponseDTO(user.getId(), user.getName()))
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

    private TeamsOfSubjectDTO convertToTeamsOfSubjectDTO(Team team) {

        return new TeamsOfSubjectDTO(
                team.getTeamId(),
                team.getTeamTitle(),
                team.getSubject().getTitle(),
                team.getRecruitmentStatus(),
                team.getMaxMemberNum(),
                team.getMaxMemberNum()
        );
    }
}
