package com.ably.test.user.domain;

import lombok.Data;


@Data
public class CodeVerification {

    private Long id;

    private String digit;

    private String code;

    private boolean isVerified;

}

