package com.example.bilda_server.domain.enums;

public enum EvaluationItem {
    MAJOR("전공 이해도"),PUNCTUALITY("시간 준수"),COMMUNICATION("의사소통 능력")
    ,PROACTIVITY("적극성"),RESPONSIBILITY("책임감");

    private final String description;

    EvaluationItem(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
