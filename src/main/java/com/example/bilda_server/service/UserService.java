package com.example.bilda_server.service;

import static com.example.bilda_server.utils.ExceptionMessage.*;

import com.example.bilda_server.auth.CustomUserDetails;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.repository.UserJpaRepository;
import com.example.bilda_server.request.ChangeNicknameRequest;
import com.example.bilda_server.request.ChangePasswordRequest;
import com.example.bilda_server.request.SignupRequest;
import com.example.bilda_server.response.ChangeNicknameResponse;
import com.example.bilda_server.response.ChangePasswordResponse;
import com.example.bilda_server.response.SignupResponse;
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
    public SignupResponse signup(SignupRequest request) {
        User newUser = User.create(request, passwordEncoder);

        userRepository.findByEmail(newUser.getEmail()).ifPresent(user -> {
	throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
            }
        );

        userRepository.save(newUser);
        return new SignupResponse(newUser.getEmail(), newUser.getPassword(), newUser.getName(),
            newUser.getStudentId(), newUser.getNickname(), newUser.getDepartment());
    }

    @Transactional
    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest,
        CustomUserDetails userDetails) {
        User target = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_BY_EMAIL));

        target.changePassword(changePasswordRequest);
        return new ChangePasswordResponse(target.getPassword());
    }

    @Transactional
    public ChangeNicknameResponse changeNickname(ChangeNicknameRequest changeNicknameRequest,
        CustomUserDetails userDetails) {
        User target = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_BY_EMAIL));

        target.changeNickname(changeNicknameRequest);
        return new ChangeNicknameResponse(target.getNickname());

    }
}
