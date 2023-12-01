package com.example.bilda_server.domain.entity;


import com.example.bilda_server.domain.enums.EvaluationItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluated_user_id")
    private User evaluatedUser; // 평가 받는 사용자

    @ManyToOne
    @JoinColumn(name = "evaluator_user_id")
    private User evaluator; // 평가하는 사용자

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team; // 평가가 속한 팀

    @ElementCollection
    @CollectionTable(name = "evaluation_scores", joinColumns = @JoinColumn(name = "evaluation_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "score")
    private Map<EvaluationItem, Integer> scores; // 평가 항목별 점수

}
