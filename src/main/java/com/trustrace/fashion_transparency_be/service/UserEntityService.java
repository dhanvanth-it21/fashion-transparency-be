package com.trustrace.fashion_transparency_be.service;

import com.trustrace.fashion_transparency_be.dao.UserEntityDao;
import com.trustrace.fashion_transparency_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.fashion_transparency_be.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityService {

    @Autowired
    private UserEntityDao userEntityDao;

    public void saveUserEntity(UserEntity userentity) {
        userEntityDao.saveUserEntity(userentity);
    }

    public Boolean existsByName(String name) {
        Boolean isExists =  userEntityDao.existsByName(name);
        return isExists;
    }

    public boolean existsByEmail(String email) {
        Boolean isExists = userEntityDao.existsByEmail(email);
        return isExists;
    }

    public Optional<UserEntity> findByName(String name) {
        Optional<UserEntity> userEntity = userEntityDao.findByName(name);
        return userEntity;
    }

    public UserEntity findByEmail(String email) {
        UserEntity userEntity = userEntityDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email id: " + email));
        return userEntity;
    }

    public UserEntity findById(String id) {

        UserEntity userEntity = userEntityDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No user exist with the provided id")
        );
        return userEntity;
    }
}
