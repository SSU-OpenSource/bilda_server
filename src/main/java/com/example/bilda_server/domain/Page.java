package com.example.bilda_server.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Page {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String userInfoMessage;
    private int temperature;
    private double reMatchingHopeRate;

    //Messages는 별도의 Entity로 존재하며, User의 teams를 통해 접근하므로 page 클래스에 필요하지 않다
    //따라서, page 클래스 내에 Messages 리스트를 직접 포함시키지 않는다.
    //별도의 비즈니스 로직을 통해 Messages를 조회한다.


    public Page(User user) {
        this.user = user;
    }
}
