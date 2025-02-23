package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.UserEntityDao;
import com.trustrace.tiles_hub_be.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean existsByEmailId(String emailId) {
        Boolean isExists = userEntityDao.existsByEmailId(emailId);
        return isExists;
    }

    public Optional<UserEntity> findByName(String name) {
        Optional<UserEntity> userEntity = userEntityDao.findByName(name);
        return userEntity;
    }
}
