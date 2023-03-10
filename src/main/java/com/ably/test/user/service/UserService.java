package com.ably.test.user.service;
import com.ably.test.user.domain.User;
import com.ably.test.error.NotFoundException;
import com.ably.test.user.dao.UserRepository;
import com.ably.test.user.domain.Verificationhistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public User signup(User user) {
        checkNotNull(user);

        Object codeinfo = userRepository.selectIsVerified(user.getTelNum());


        if (codeinfo == null || Boolean.parseBoolean(codeinfo.toString()) == false) {
            throw new NotFoundException("휴대폰 번호 인증을 해 주시기 바랍니다.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User getMyInfo(String loginId, String phoneNumber) {
        checkNotNull(loginId, "loginId must be provided.");
        checkNotNull(phoneNumber, "code must be provided.");

        User user = userRepository.findByLoginId(loginId);

        if (user == null) {
            throw new NotFoundException("정보를 찾을 수 없습니다.");
        }

        Object codeinfo = userRepository.selectIsVerified(phoneNumber);

        if (codeinfo == null || Boolean.parseBoolean(codeinfo.toString()) == false) {
            throw new IllegalArgumentException("휴대폰 번호 인증을 해 주시기 바랍니다.");
        }

        return user;
    }

    @Transactional
    public User login(String loginId, String password) {
        checkNotNull(loginId, "loginId must be provided.");
        checkNotNull(password, "password must be provided.");

        User user = userRepository.findByLoginId(loginId);

        if (user == null) {
            throw new NotFoundException("정보를 찾을 수 없습니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("패스워드가 올바르지 않습니다.");
        }


        return user;
    }

    @Transactional
    public String getVerifyCode(String phoneNumber) {
        checkNotNull(phoneNumber, "phoneNumber must be provided.");

        String code = getRandomCode();

        userRepository.insertPhoneNumberAndCode(phoneNumber, code, false);

        return code;
    }

    public String getRandomCode() {
        String randomCode = "";
        for (int i = 0; i < 6; i++) {
            randomCode += (int) (Math.random() * 10);
        }
        return randomCode;
    }

    @Transactional
    public String insertVerificationCode(String phoneNumber, String code) {
        checkNotNull(phoneNumber, "phoneNumber must be provided.");
        checkNotNull(code, "code must be provided.");

        String result = "fail";

        Object codeinfo = userRepository.selectCode(phoneNumber);

        if (codeinfo == null) {
            throw new NotFoundException("Code not found.");
        }

        if (codeinfo.toString().equals(code)) {
            userRepository.updateVerification(phoneNumber);
            result = "success";
        }

        return result;
    }

    @Transactional
    public String resetPassword(String phoneNumber, String password) {
        checkNotNull(phoneNumber, "phoneNumber must be provided.");
        checkNotNull(password, "password must be provided.");

        String result = "fail";

        Object codeinfo = userRepository.selectIsVerified(phoneNumber);

        if (codeinfo == null || Boolean.parseBoolean(codeinfo.toString()) == false) {
            throw new IllegalArgumentException("휴대폰 번호 인증을 해 주시기 바랍니다.");
        }

        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new NotFoundException("정보를 찾을 수 없습니다.");
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        result = "success";

        return result;
    }

    public User findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

}