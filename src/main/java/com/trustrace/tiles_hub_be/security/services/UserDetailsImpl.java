package com.trustrace.tiles_hub_be.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trustrace.tiles_hub_be.model.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L; // Serializable version identifier

    private String id; // Unique identifier for the user
    private String username; // Username of the user
    private String email; // Email address of the user

    @JsonIgnore // Prevent serialization of the password field
    private String password; // Password of the user

    private Collection<? extends GrantedAuthority> authorities; // Collection of user's authorities (roles)

    /**
     * Constructor to initialize UserDetailsImpl.
     *
     * @param id           The unique identifier of the user.
     * @param username     The username of the user.
     * @param email        The email of the user.
     * @param password     The password of the user.
     * @param authorities  The collection of user's authorities.
     */
    public UserDetailsImpl(String id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id; // Set user ID
        this.username = username; // Set username
        this.email = email; // Set email
        this.password = password; // Set password
        this.authorities = authorities; // Set authorities
    }

/**
     * Builds a UserDetailsImpl instance from a User object.
     *
     * @param userEntity The User object.
     * @return A UserDetailsImpl instance.
     */
    public static UserDetailsImpl build(UserEntity userEntity) {
        // Map the roles of the user to GrantedAuthority
        List<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Convert each role to SimpleGrantedAuthority
                .collect(Collectors.toList()); // Collect into a list

        // Return a new UserDetailsImpl object
        return new UserDetailsImpl(
                userEntity.getId(), // Set user ID
                userEntity.getName(), // Set username
                userEntity.getEmail(), // Set email
                userEntity.getPassword(), // Set password
                authorities // Set authorities
        );
    }

    public String getId() {
        return id; // Return user ID
    }

    public String getEmail() {
        return email; // Return email
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // Return user's authorities
    }

    @Override
    public String getPassword() {
        return password; // Return password
    }

    @Override
    public String getUsername() {
        return username; // Return username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Account always enabled
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // If the objects are the same
        if (o == null || getClass() != o.getClass()) return false; // If the object is null or of different class
        UserDetailsImpl user = (UserDetailsImpl) o; // Cast the object to UserDetailsImpl
        return Objects.equals(id, user.id); // Return whether the IDs are equal
    }
}
