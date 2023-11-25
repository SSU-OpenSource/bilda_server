package com.example.bilda_server.service;


import com.example.bilda_server.domain.entity.Evaluation;
import com.example.bilda_server.domain.entity.Page;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.repository.EvaluationRepository;
import com.example.bilda_server.repository.PageRepository;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.request.EvaluationRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final UserJpaRepository userRepository;
    private final PageRepository pageRepository;

    @Transactional
    public void createAndReflectEvaluation(EvaluationRequestDTO evaluationRequestDTO) {
        User evaluatedUser = userRepository.findById(evaluationRequestDTO.getEvaluatedUserId())
                .orElseThrow(() -> new EntityNotFoundException("Evaluated User not found"));
        User evaluatorUser = userRepository.findById(evaluationRequestDTO.getEvaluatorUserId())
                .orElseThrow(() -> new EntityNotFoundException("Evaluator User not found"));

        Page evaluatedUserPage = evaluatedUser.getMyPage();
        if (evaluatedUserPage == null) {
            throw new RuntimeException("Evaluated User's page not found");
        }

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluatedUser(evaluatedUser);
        evaluation.setEvaluator(evaluatorUser);
        evaluation.setScores(evaluationRequestDTO.getScores());

        evaluationRequestDTO.getScores().forEach(evaluatedUserPage::addEvaluationScore);

        pageRepository.save(evaluatedUserPage);

    }

}
