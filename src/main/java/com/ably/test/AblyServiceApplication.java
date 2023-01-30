package com.ably.test;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class AblyServiceApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(AblyServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }

}
