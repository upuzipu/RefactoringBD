package com.example.repository;

import com.example.model.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = String.class)
public interface UserRepository {
    @Query("select person.person_email as person_email, p.password as password from person " +
            "join users_password p on person.person_id = p.person_id " +
            "where person_email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Modifying
    @Query("call add_user(:username, :email, :password)")
    void createUser(@Param("username") String username,
                    @Param("email") String email,
                    @Param("password") String password);

    @Query("select exists(select * from person where person_email = :login)")
    boolean isUserExists(@Param("login") String email);
}
