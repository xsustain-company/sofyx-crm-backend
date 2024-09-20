package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    @Query("select u from User u where u.email=:v1")
    public Optional<User> findUserByEmail(@Param("v1") String email);
    boolean existsByEmail(String email);
    User findByTokenToValidate(Long tokenToValidate);
    Optional<User> findByEmail(String email);
    User findByTokenToForgotPassword(Long tokenToForgotPassword);
}
