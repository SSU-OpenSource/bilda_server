package com.example.bilda_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    //page 엔티티에 정의된 User 객체에 대한 참조를 나타낸다.
    //cascade 는 User엔티티와 관련된 Page엔티티에 대한 영속성 관리 작업(저장, 조회, 삭제, 업데이트)이 User 엔티티에서도 적용되게한다.
    //fetch = FetchType.LAZY는 User 엔티티를 로드할 때 Page 엔티티를 즉시 로드하지 않고, 필요할 때 로드되도록 설정한다.
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Page myPage;

    @ManyToMany(mappedBy = "users")
    private List<Team> teams;

    @ManyToMany
    @JoinTable(
            name = "user_subject",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_code")
    )
    private Set<Subject> subjects = new HashSet<>();
}
