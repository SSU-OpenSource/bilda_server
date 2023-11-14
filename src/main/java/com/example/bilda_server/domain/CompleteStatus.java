package com.example.bilda_server.domain;

public enum CompleteStatus {

    COMPLETE("완료"), PROGRESS("진행중");

    private final String description;

    CompleteStatus(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
