package com.example.bilda_server.repository;

import com.example.bilda_server.domain.entity.Evaluation;
import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    // 특정 평가자가 특정 팀의 특정 사용자를 평가했는지 확인하는 메서드
    boolean existsByEvaluatorAndEvaluatedUserAndTeam(User evaluator, User evaluatedUser, Team team);

    // 주어진 평가자와 팀에 대해 평가된 모든 엔티티를 찾는 메서드
    List<Evaluation> findByEvaluatorAndTeam(User evaluator, Team team);

}
