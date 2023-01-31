package com.ably.test.user.dao;


import com.ably.test.user.domain.User;
import com.ably.test.user.domain.Verificationhistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE login_id = ?1 AND password = ?2", nativeQuery = true)
    User findByLoginIdAndPassword(String loginId, String password);

    @Query(value = "SELECT * FROM user WHERE login_id = ?1", nativeQuery = true)
    User findByLoginId(String loginId);

    @Query(value = "INSERT INTO verificationhistory (digit, code, verified) VALUES (?1, ?2, ?3)", nativeQuery = true)
    @Modifying
    @Transactional
    void insertPhoneNumberAndCode(String phoneNumber, String code, boolean verified);

    @Query(value = "SELECT code FROM verificationhistory  WHERE digit = ?1", nativeQuery = true)
    Object selectCode(String digit);

    @Query(value = "UPDATE verificationhistory SET verified = true WHERE digit = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateVerification(String digit);

    @Query(value = "SELECT verified FROM verificationhistory WHERE digit = ?1", nativeQuery = true)
    Object selectIsVerified(String digit);

    @Query(value = "SELECT * FROM user WHERE tel_num = ?1", nativeQuery = true)
    User findByPhoneNumber(String phoneNumber);

}


