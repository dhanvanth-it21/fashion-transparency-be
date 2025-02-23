package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.model.UserEntity;
import com.trustrace.tiles_hub_be.template.UserEntityTemplate;
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

    public Boolean existsByEmailId(String emailId) {
        Boolean isExists = userEntityTemplate.existsByEmailId(emailId);
        return isExists;
    }

    public Optional<UserEntity> findByName(String name) {
        Optional<UserEntity> userEntity = userEntityTemplate.findByName(name);
        return userEntity;
    }
}
