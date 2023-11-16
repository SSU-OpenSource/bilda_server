package com.example.bilda_server.Repository;

import com.example.bilda_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

}
