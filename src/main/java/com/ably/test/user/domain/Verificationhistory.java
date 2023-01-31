package com.ably.test.user.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;


@Data
@Entity(name = "Verificationhistory")
@Table(name = "verificationhistory")
public class Verificationhistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String digit;

    //@Column(name = "verified", nullable = true)
    private boolean verified;

}

