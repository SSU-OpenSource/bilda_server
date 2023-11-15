package com.example.bilda_server.domain;

import com.example.bilda_server.domain.CompleteStatus;
import com.example.bilda_server.domain.RecruitmentStatus;
import com.example.bilda_server.domain.Subject;
import com.example.bilda_server.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
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


    //pending은 보류중이라는 뜻이다.
    //생성된 팀에 대해서 참가신청을 보낸 유저들을 관리하기 위한 list이다.
    //teamLeader는 리스트를 검토하여 사용자의 join여부를 결정한다. 승인된 경우는 users리스트로 이동한다.
    @ManyToMany
    @JoinTable(
            name = "pending_user_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns =  @JoinColumn(name ="user_id")
    )

    private List<User> pendingUsers;

}
