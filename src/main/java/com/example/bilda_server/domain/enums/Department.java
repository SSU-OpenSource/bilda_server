package com.example.bilda_server.domain.enums;

public enum Department {

    COMPUTER("컴퓨터 학부"), SOFTWARE("소프트웨어 학부"), EXTRA("기타");

    private final String department;

    Department(String department) {
        this.department = department;
    }

    public String getDescription() {
        return department;
    }
}
