package com.example.bilda_server.service;

import com.example.bilda_server.mapper.TeamMapper;
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
    private final TeamMapper teamMapper;

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
        return teamMapper.ToTeamResponseDTO(team);
    }

    public List<TeamsOfSubjectDTO> findTeamsBySubjectId(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("subject not found"));

        List<Team> teams = teamRepository.findBySubject(
                subject
        );

        return teams.stream()
                .map(teamMapper::ToTeamsOfSubjectDTO)
                .collect(Collectors.toList());
    }


    public List<TeamResponseDTO> findTeamsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        return user.getTeams().stream()
                .map(teamMapper::ToTeamResponseDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public void addPendingUserToTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("team not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (team.getPendingUsers().contains(user)) {
            throw new IllegalStateException("User already requested to join the team");
        }

        team.getPendingUsers().add(user);

        teamRepository.save(team);
    }

}
