package com.ably.test.user.domain;


import lombok.Data;



@Data
public class AuthRequest {

    private String userId;
    private String email;
    private String password;

    private String name;

}
