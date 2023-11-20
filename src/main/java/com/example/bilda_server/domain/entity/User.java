package com.example.bilda_server.domain.entity;

import com.example.bilda_server.domain.enums.Department;
import com.example.bilda_server.request.ChangeNicknameRequest;
import com.example.bilda_server.request.ChangePasswordRequest;
import com.example.bilda_server.request.SignupRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String email;
    private String password;
    private String name;
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

    @Builder
    public User(Long userId, String email, String password, String nickname, String name,
        String studentId,
        Department department, Page myPage, List<Team> teams, Set<Subject> subjects) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.studentId = studentId;
        this.department = department;
        this.myPage = myPage;
        this.teams = teams;
        this.subjects = subjects;
    }

    public static User create(SignupRequest request, PasswordEncoder passwordEncoder) {
        return User.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .name(request.name())
            .studentId(request.studentId())
            .nickname(request.nickname())
            .department(request.department())
            .build();
    }

    public boolean matchPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(this.password, password);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        this.password = changePasswordRequest.password();
    }

    public void changeNickname(ChangeNicknameRequest changeNicknameRequest) {
        this.nickname = changeNicknameRequest.nickname();
    }
}
