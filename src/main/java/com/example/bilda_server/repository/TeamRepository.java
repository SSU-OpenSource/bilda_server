package com.example.bilda_server.repository;

import com.example.bilda_server.domain.entity.Subject;
import com.example.bilda_server.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findBySubject(Subject subject);
}
