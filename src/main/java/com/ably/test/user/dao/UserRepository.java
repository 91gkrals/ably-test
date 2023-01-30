package com.ably.test.user.dao;


import com.ably.test.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE login_id = ?1 AND password = ?2", nativeQuery = true)
    User findByLoginIdAndPassword(String loginId, String password);

    @Query(value = "SELECT * FROM user WHERE login_id = ?1", nativeQuery = true)
    User findByLoginId(String loginId);

    @Query(value = "INSERT INTO verificationHistory (digit, code) VALUES (?1, ?2)", nativeQuery = true)
    @Modifying
    @Transactional
    void insertPhoneNumberAndCode(String phoneNumber, String code);

    @Query(value = "SELECT code FROM verificationHistory WHERE digit = ?1", nativeQuery = true)
    String selectCode(String phoneNumber);

    @Query(value = "UPDATE verificationHistory SET isverified = true WHERE digit = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateVerification(String digit);

    @Query(value = "SELECT isverified FROM verificationHistory WHERE digit = ?1", nativeQuery = true)
    boolean selectIsVerified(String digit);

    @Query(value = "SELECT * FROM user WHERE tel_num = ?1", nativeQuery = true)
    User findByPhoneNumber(String phoneNumber);

}


