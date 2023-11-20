package com.example.bilda_server.service;

import static com.example.bilda_server.utils.ExceptionMessage.*;

import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.request.ChangeNicknameRequest;
import com.example.bilda_server.request.ChangePasswordRequest;
import com.example.bilda_server.request.SignupRequest;
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
    public User signup(SignupRequest request) {
        User newUser = User.create(request, passwordEncoder);

        userRepository.findByEmail(newUser.getEmail()).ifPresent(user -> {
	throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
            }
        );

        userRepository.save(newUser);
        return newUser;
    }

    @Transactional
    public User changePassword(ChangePasswordRequest changePasswordRequest,
        CustomUserDetails userDetails) {
        User target = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_BY_EMAIL));

        target.changePassword(changePasswordRequest);
        return target;
    }

    @Transactional
    public User changeNickname(ChangeNicknameRequest changeNicknameRequest,
        CustomUserDetails userDetails) {
        User target = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_BY_EMAIL));

        target.changeNickname(changeNicknameRequest);
        return target;

    }
}
