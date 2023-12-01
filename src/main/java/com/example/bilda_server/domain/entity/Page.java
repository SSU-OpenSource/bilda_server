package com.example.bilda_server.domain.entity;


import com.example.bilda_server.domain.enums.EvaluationItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Page {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 평가 점수 관리를 위한 필드
    @ElementCollection
    @CollectionTable(name = "page_evaluation_scores", joinColumns = @JoinColumn(name = "page_id"))
    @MapKeyEnumerated(EnumType.STRING) // Enum 타입을 사용
    @MapKeyColumn(name = "evaluation_item")
    private Map<EvaluationItem, EvaluationScore> evaluationScores = new EnumMap<>(EvaluationItem.class);

    public Page(User user) {
        this.user = user;
    }


    public void addEvaluationScore(EvaluationItem item, int score) {
        EvaluationScore evaluationScore = evaluationScores.getOrDefault(item, new EvaluationScore());
        evaluationScore.addScore(score);
        evaluationScores.put(item, evaluationScore);
    }

    public double getAverageScore(EvaluationItem item) {
        EvaluationScore evaluationScore = evaluationScores.get(item);
        return evaluationScore !=null ? evaluationScore.getAverage() : 0;
    }

    public int getHighScoreCount(EvaluationItem item) {
        EvaluationScore evaluationScore = evaluationScores.get(item);
        return evaluationScore !=null ? evaluationScore.getHighScoreCount() : 0;
    }

    @Embeddable
    @Getter
    @Setter
    public static class EvaluationScore{
        private int totalScore;
        private int count;
        private int highScoreCount; //80점 이상인 평가의 수

        public void addScore(int score){
            this.totalScore +=  score;
            this.count++;
            if (score >= 80) {
                this.highScoreCount++;
            }
        }

        public double getAverage() {
            return count > 0 ? (double) totalScore/count:0;
        }

    }
}
