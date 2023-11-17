package com.example.bilda_server.Repository;

import com.example.bilda_server.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    //JpaRepository 에서 제공하는 findById와 충돌
    //Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
