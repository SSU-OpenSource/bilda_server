package com.example.bilda_server.repository;

import com.example.bilda_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

}
