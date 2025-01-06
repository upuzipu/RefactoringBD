package com.example.repository;

import com.example.model.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for working with the {@link User} entity.
 * This interface provides methods for working with users, including searching by email, creating a new user,
 * and checking if a user exists in the database.
 */
@RepositoryDefinition(domainClass = User.class, idClass = String.class)
public interface UserRepository {

    /**
     * Find a user by their email address.
     *
     * @param email the email address of the user
     * @return an Optional containing the user with the specified email, or empty if the user is not found
     */
    @Query("select person.person_email as person_email, p.password as password from person " +
            "join users_password p on person.person_id = p.person_id " +
            "where person_email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    /**
     * Create a new user.
     *
     * @param username the username of the user
     * @param email the email address of the user
     * @param password the password of the user
     */
    @Modifying
    @Query("call add_user(:username, :email, :password)")
    void createUser(@Param("username") String username,
                    @Param("email") String email,
                    @Param("password") String password);

    /**
     * Check if a user exists by their email address.
     *
     * @param email the email address of the user
     * @return true if a user with the specified email exists in the database, otherwise false
     */
    @Query("select exists(select * from person where person_email = :login)")
    boolean isUserExists(@Param("login") String email);
}
