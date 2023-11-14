package com.example.bilda_server.domain;

import com.example.bilda_server.domain.CompleteStatus;
import com.example.bilda_server.domain.RecruitmentStatus;
import com.example.bilda_server.domain.Subject;
import com.example.bilda_server.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teamId;

    @ManyToOne
    private User leader;

    @ManyToMany
    @JoinTable(
            name = "user_team",
            joinColumns = @JoinColumn(name="team_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private List<User> users;

    @ManyToOne
    private Subject subject;

    private String teamTitle;
    private LocalDate recruitmentEndDate;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatus recruitmentStatus;

    @Enumerated(EnumType.STRING)
    private CompleteStatus completeStatus;

    private String teamInfoMessage;
    private LocalDate buildStartDate;

}
