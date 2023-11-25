package com.example.bilda_server.service;


import com.example.bilda_server.domain.entity.Evaluation;
import com.example.bilda_server.domain.entity.Page;
import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.repository.EvaluationRepository;
import com.example.bilda_server.repository.PageRepository;
import com.example.bilda_server.repository.TeamRepository;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.request.EvaluationRequestDTO;
import com.example.bilda_server.response.TeamMemberEvaluationDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final UserJpaRepository userRepository;
    private final PageRepository pageRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void createAndReflectEvaluation(EvaluationRequestDTO evaluationRequestDTO) {
        User evaluatedUser = userRepository.findById(evaluationRequestDTO.getEvaluatedUserId())
                .orElseThrow(() -> new EntityNotFoundException("Evaluated User not found"));
        User evaluatorUser = userRepository.findById(evaluationRequestDTO.getEvaluatorUserId())
                .orElseThrow(() -> new EntityNotFoundException("Evaluator User not found"));

        Team team = teamRepository.findById(evaluationRequestDTO.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        Page evaluatedUserPage = evaluatedUser.getMyPage();
        if (evaluatedUserPage == null) {
            throw new RuntimeException("Evaluated User's page not found");
        }

        if (evaluationRepository.existsByEvaluatorAndEvaluatedUserAndTeam(evaluatorUser, evaluatedUser, team)) {
            throw new RuntimeException("Duplicate evaluation not allowed for the same team and user");
        }

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluatedUser(evaluatedUser);
        evaluation.setEvaluator(evaluatorUser);
        evaluation.setTeam(team);
        evaluation.setScores(evaluationRequestDTO.getScores());

        evaluationRequestDTO.getScores().forEach(evaluatedUserPage::addEvaluationScore);

        evaluationRepository.save(evaluation);
        pageRepository.save(evaluatedUserPage);

    }

    public List<TeamMemberEvaluationDTO> getEvaluationStatusOfTeamMembers(Long userId, Long teamId) {
        User evaluator = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        List<Evaluation> evaluations = evaluationRepository.findByEvaluatorAndTeam(evaluator, team);

        List<TeamMemberEvaluationDTO> evaluationStatuses = new ArrayList<>();
        for (User member : team.getUsers()) {
            if (!member.equals(evaluator)) {
                boolean hasEvaluated = evaluations.stream()
                        .anyMatch(evaluation -> evaluation.getEvaluatedUser().equals(member));
                evaluationStatuses.add(new TeamMemberEvaluationDTO(member.getId(), member.getName(), hasEvaluated));
            }
        }

        return evaluationStatuses;

    }

}
