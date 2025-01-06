package com.example.model.entity;

import com.example.model.enumeration.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User entity implementing the {@link UserDetails} interface.
 * Used to represent a user in the security system.
 * Contains user information, such as email, nickname, password, and registration date.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    /**
     * User's email. Used for authentication.
     */
    private String personEmail;

    /**
     * User's nickname.
     */
    private String personNickname;

    /**
     * Unique identifier for the user.
     */
    private long personId;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's birth date.
     */
    private Date birthDate;

    /**
     * User's registration date in the system.
     */
    private Date registrationDate;

    /**
     * Constructor to create a user with email and password.
     * @param personEmail User's email
     * @param password User's password
     */
    public User(String personEmail, String password) {
        this.personEmail = personEmail;
        this.password = password;
    }

    /**
     * Constructor to create a user with email, nickname, and password.
     * @param personEmail User's email
     * @param personNickname User's nickname
     * @param password User's password
     */
    public User(String personEmail, String personNickname, String password) {
        this.personEmail = personEmail;
        this.personNickname = personNickname;
        this.password = password;
    }

    /**
     * Get the username (which is the user's email).
     * @return User's email
     */
    @Override
    public String getUsername() {
        return personEmail;
    }

    /**
     * Get the user's password.
     * @return User's password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get the list of authorities (roles) for the user.
     * @return User's role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Role.USER);  // Assuming the user always has the "USER" role
    }

    /**
     * Check if the user's account has expired.
     * @return true if the account is not expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the user's account is locked.
     * @return true if the account is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the user's credentials (password) have expired.
     * @return true if the credentials are not expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the user's account is enabled.
     * @return true if the account is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
