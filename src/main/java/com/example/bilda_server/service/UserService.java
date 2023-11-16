package com.example.bilda_server.service;

import com.example.bilda_server.domain.User;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signUp(UserSignUpRequest request) {
        User newUser = User.create(request, passwordEncoder);

        userRepository.findByEmail(newUser.getEmail()).ifPresent(user -> {
	throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
            }
        );

        userRepository.save(newUser);
        return newUser;
    }
}
