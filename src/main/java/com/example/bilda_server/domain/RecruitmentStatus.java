package com.example.bilda_server.domain;

public enum RecruitmentStatus {

    RECRUIT("모집중"), BUILDING("모집 완료");

    private final String description;

    RecruitmentStatus(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }



}
