package com.trustrace.tiles_hub_be.security.services;

import com.trustrace.tiles_hub_be.model.user.UserEntity;
import com.trustrace.tiles_hub_be.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserDetailsService to load user-specific data.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserEntityService userEntityService;

    /**
     * Loads user details by username.
     *
     * @param email The username of the user.
     * @return UserDetails containing user information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    @Transactional // Ensures that the method is transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Attempt to find the user by username
        UserEntity userEntity = userEntityService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email id: " + email));

        // Return UserDetails implementation for the found user
        return UserDetailsImpl.build(userEntity);
    }

}
