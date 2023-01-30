package com.ably.test.user.service;
import com.ably.test.user.domain.User;
import com.ably.test.error.NotFoundException;
import com.ably.test.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User getMyInfo(String loginId, String phoneNumber) {
        checkNotNull(loginId, "loginId must be provided.");
        checkNotNull(phoneNumber, "code must be provided.");

        User user = userRepository.findByLoginId(loginId);

        if (user == null) {
            throw new NotFoundException("User not found.");
        }

        boolean isVerified = userRepository.selectIsVerified(phoneNumber);

        if (isVerified == false) {
            throw new IllegalArgumentException("It is not verified.");
        }

        return user;
    }

    @Transactional
    public User login(String loginId, String password) {
        checkNotNull(loginId, "loginId must be provided.");
        checkNotNull(password, "password must be provided.");

        User user = userRepository.findByLoginId(loginId);

        if (user == null) {
            throw new NotFoundException("User not found.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Password is not correct.");
        }


        return user;
    }

    @Transactional
    public String getVerifyCode(String phoneNumber) {
        checkNotNull(phoneNumber, "phoneNumber must be provided.");

        String code = getRandomCode();

        userRepository.insertPhoneNumberAndCode(phoneNumber, code);

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

        String codeSearched = userRepository.selectCode(phoneNumber);

        if (codeSearched == null) {
            throw new NotFoundException("Code not found.");
        }

        if (codeSearched.equals(code)) {
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

        boolean isVerified = userRepository.selectIsVerified(phoneNumber);

        if (isVerified == false) {
            throw new IllegalArgumentException("It is not verified.");
        }

        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new NotFoundException("User not found.");
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