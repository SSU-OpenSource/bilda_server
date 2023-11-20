package com.example.bilda_server.service;

import com.example.bilda_server.mapper.TeamMapper;
import com.example.bilda_server.mapper.UserMapper;
import com.example.bilda_server.repository.SubjectRepository;
import com.example.bilda_server.repository.TeamRepository;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.domain.enums.CompleteStatus;
import com.example.bilda_server.domain.enums.RecruitmentStatus;
import com.example.bilda_server.request.CreateTeamRequest;
import com.example.bilda_server.response.PendingUserDTO;
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
    private final UserMapper userMapper;
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

    //join요청에 해당하는 메서드
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

    public List<PendingUserDTO> getPendingUsers(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not Found"));

        return team.getPendingUsers().stream()
                .map(userMapper::ToPendingUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void approvePendingUser(Long teamId, Long leaderId, Long pendingUserId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("team not found"));
        User pendingUser = userRepository.findById(pendingUserId)
                .orElseThrow(() -> new EntityNotFoundException("pendingUser not found"));

        if (!team.getLeader().getUserId().equals(leaderId)) {
            throw new IllegalStateException("Only team leader can approve pending user");
        }

        if (!team.getPendingUsers().contains(pendingUser)) {
            throw new IllegalStateException("no pending request from the user to this team");
        }

        team.getPendingUsers().remove(pendingUser);
        team.getUsers().add(pendingUser);


        teamRepository.save(team);

        //이건 고민해보자
        // leader가 팀가입을 수락하면 해당 user의 subject set에서 과목 삭제
    }

    @Transactional
    public void rejectPendingUser(Long teamId, Long leaderId, Long pendingUserId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("team not found"));
        User pendingUser = userRepository.findById(pendingUserId)
                .orElseThrow(() -> new EntityNotFoundException("pendingUser not found"));

        if (!team.getLeader().getUserId().equals(leaderId)) {
            throw new IllegalStateException("Only team leader can approve pending user");
        }

        if (!team.getPendingUsers().contains(pendingUser)) {
            throw new IllegalStateException("no pending request from the user to this team");
        }

        team.getPendingUsers().remove(pendingUser);

        teamRepository.save(team);

    }

}
