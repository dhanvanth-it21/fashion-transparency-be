package com.trustrace.fashion_transparency_be.dao;

import com.trustrace.fashion_transparency_be.model.user.UserEntity;
import com.trustrace.fashion_transparency_be.template.UserEntityTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityDao {

    @Autowired
    private UserEntityTemplate userEntityTemplate;

    public void saveUserEntity(UserEntity userEntity) {
        userEntityTemplate.save(userEntity);
    }

    public Boolean existsByName(String name) {
        Boolean isExists =  userEntityTemplate.existsByName(name);
        return isExists;
    }

    public Boolean existsByEmail(String email) {
        Boolean isExists = userEntityTemplate.existsByEmail(email);
        return isExists;
    }

    public Optional<UserEntity> findByName(String name) {
        Optional<UserEntity> userEntity = userEntityTemplate.findByName(name);
        return userEntity;
    }

    public Optional<UserEntity> findById(String id) {
        return userEntityTemplate.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        Optional<UserEntity> userEntity = userEntityTemplate.findByEmail(email);
        return userEntity;
    }
}
