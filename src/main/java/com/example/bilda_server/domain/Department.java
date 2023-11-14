package com.example.bilda_server.domain;

public enum Department {

    COM("컴퓨터학부"), SOFT("소프트학부"), ETC("기타");

    private final String description;

    Department(String description)    {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
