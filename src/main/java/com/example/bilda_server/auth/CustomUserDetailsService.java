package com.example.bilda_server.auth;

import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 계정이 존재하지 않습니다."));
        return new CustomUserDetails(user);
    }
}
