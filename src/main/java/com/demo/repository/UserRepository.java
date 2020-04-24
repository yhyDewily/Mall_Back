package com.demo.repository;

import com.demo.dataobject.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT count(1) from mall.user where username=?1", nativeQuery = true)
    int checkUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT count(1) from mall.user WHERE email=?1", nativeQuery = true)
    int checkEmail(String email);

    @Query(value = "SELECT question from mall.user WHERE  username=?1", nativeQuery = true)
    String findQuestionByUsername(String username);

    @Query(value = "SELECT count(1) from mall.user where username=?1 and question=?2 and anser=?3", nativeQuery = true)
    int checkAnswer(String username, String question, String answer);

    @Modifying
    @Transactional
    @Query(value = "UPDATE mall.user SET password =?2 where username=?1", nativeQuery = true)
    int updatePasswordByUsername(String username, String passwordNew);

    @Query(value = "SELECT count(1) from mall.user where password=?1 and id=?2", nativeQuery = true)
    int checkPassword(String password, Integer userId);

    @Query(value = "SELECT count(1) from mall.user where email=?1 and id=?2", nativeQuery = true)
    int checkEmailByUserId(String email, Integer id);

    @Query(value = "SELECT * from mall.user where id=?1", nativeQuery = true)
    User findByUserId(Integer id);

}
