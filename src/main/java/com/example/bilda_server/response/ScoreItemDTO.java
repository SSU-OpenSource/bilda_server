package com.example.bilda_server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScoreItemDTO {

    private String evaluationItemName;
    private double averageScore;
    private int highScoreCount;

}
