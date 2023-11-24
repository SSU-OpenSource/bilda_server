package com.example.bilda_server.service;

import static com.example.bilda_server.utils.ExceptionMessage.*;

import com.example.bilda_server.repository.UserRepository;
import com.example.bilda_server.utils.RedisUtil;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final JavaMailSender emailSender;

    public void sendMail(String to, String sub, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(sub);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendVerificationMail(String email) throws RuntimeException {
        String authCode = this.createCode();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(USER_ALREADY_EXIST);
        }
        if (email.equals(null)) {
            throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
        }
        redisUtil.setDataExpire(AUTH_CODE_PREFIX + email, authCode, 5 * 60 * 1000);
        sendMail(email, "가입 인증 이메일입니다.", authCode);
    }

    public void verifyEmail(String email, String authCode) throws NotFoundException {
        String redisAuthCode = redisUtil.getData(AUTH_CODE_PREFIX + email);
        if (email == null) {
            throw new NotFoundException("유효하지 않은 이메일입니다.");
        }
        if (!redisAuthCode.equals(authCode)) {
            throw new IllegalArgumentException();
        }
        redisUtil.deleteData(email);
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
	builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }


}
