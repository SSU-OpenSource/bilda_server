package com.example.bilda_server.request;

import com.example.bilda_server.domain.enums.EvaluationItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class EvaluationRequestDTO {

    private Long evaluatedUserId;
    private Long evaluatorUserId;
    private Long teamId;
    private Map<EvaluationItem, Integer> scores;

}
