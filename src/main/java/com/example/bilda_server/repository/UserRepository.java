package com.example.bilda_server.Repository;

import com.example.bilda_server.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
