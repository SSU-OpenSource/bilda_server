package com.example.bilda_server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gpt_answer", length = 1500, nullable = false)
    private String answer;

    public Answer(String answer) {
        this.answer = answer;
    }

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate lastModifiedDate;
}