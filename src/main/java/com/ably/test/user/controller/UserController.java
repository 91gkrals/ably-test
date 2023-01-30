package com.ably.test.user.controller;

import com.ably.test.user.domain.User;
import com.ably.test.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder;
    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(method= RequestMethod.POST, path="/users/add")
    public User signupUserController(@RequestBody User user){
        User user_result = userService.signup(user);
        return user_result;
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public User login(@RequestBody User user) {
        User userSearched = userService.login(user.getLoginId(), user.getPassword());
        return userSearched;
    }


    @RequestMapping(value = "/users/verifycode/{phoneNumber}", method = RequestMethod.GET)
    public String getRandomCode(@PathVariable String phoneNumber) {
        String randomCode = userService.getVerifyCode(phoneNumber);
        return randomCode;
    }

    @RequestMapping(value = "/users/insertcode/{phoneNumber}/{code}", method = RequestMethod.GET)
    public String insertVerificationCode(@PathVariable String phoneNumber, @PathVariable String code) {
        String result = userService.insertVerificationCode(phoneNumber, code);
        return result;
    }

    @RequestMapping(value = "/users/myinfo/{loginId}/{phoneNumber}", method = RequestMethod.GET)
    public User getMyInfo(@PathVariable String loginId, @PathVariable String phoneNumber) {
        User user = userService.getMyInfo(loginId, phoneNumber);
        return user;
    }

    @RequestMapping(value = "/users/resetpassword/{phoneNumber}/{newpassword}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable String phoneNumber, @PathVariable String newpassword) {
        String result = userService.resetPassword(phoneNumber, newpassword);
        return result;
    }

    @RequestMapping(value = "/{loginId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String loginId) {
        return userService.findByLoginId(loginId);
    }


}
