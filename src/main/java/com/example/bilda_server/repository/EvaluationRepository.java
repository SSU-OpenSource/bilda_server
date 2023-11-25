package com.example.bilda_server.repository;

import com.example.bilda_server.domain.entity.Evaluation;
import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    // 특정 평가자가 특정 팀의 특정 사용자를 평가했는지 확인하는 메서드
    boolean existsByEvaluatorAndEvaluatedUserAndTeam(User evaluator, User evaluatedUser, Team team);

}
