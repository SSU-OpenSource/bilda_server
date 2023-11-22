package com.example.bilda_server.mapper;

import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.response.TeamResponseDTO;
import com.example.bilda_server.response.TeamsOfSubjectDTO;
import com.example.bilda_server.response.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamMapper {

    public TeamResponseDTO ToTeamResponseDTO(Team team) {

        List<UserResponseDTO> memberDTOs = team.getUsers().stream()
                .map(user -> new UserResponseDTO(user.getUserId(), user.getName()))
                .toList();

        return new TeamResponseDTO(
                team.getTeamId(),
                team.getLeader().getUserId(),
                team.getTeamTitle(),
                team.getSubject().getTitle(),
                team.getLeader().getName(),
                team.getRecruitmentStatus(),
                team.getCompleteStatus(),
                team.getBuildStartDate(),
                memberDTOs
        );
    }

    //과목에 해당하는 팀들의 정보들을 불러온다.
    public TeamsOfSubjectDTO ToTeamsOfSubjectDTO(Team team) {

        return new TeamsOfSubjectDTO(
                team.getTeamId(),
                team.getTeamTitle(),
                team.getSubject().getTitle(),
                team.getRecruitmentStatus(),
                team.getUsers().size(),
                team.getMaxMemberNum()
        );
    }
}
