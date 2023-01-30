package com.ably.test.user.domain;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String loginId;

    private String password;

    private String realName;

    private String telNum;

}

