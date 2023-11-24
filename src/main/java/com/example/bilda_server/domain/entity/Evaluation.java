package com.example.bilda_server.domain.entity;


import jakarta.persistence.*;
import lombok.*;

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

    private int score; // 평가 점수
    private String comment; // 평가 코멘트
}
