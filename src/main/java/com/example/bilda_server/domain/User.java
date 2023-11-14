package com.example.bilda_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String email;
    private String password;
    private String nickname;
    private String studentId;


    @Enumerated(EnumType.STRING)
    private Department department;

    //private Page myPage;

    @ManyToMany(mappedBy = "users")
    private List<Team> teams;
}
