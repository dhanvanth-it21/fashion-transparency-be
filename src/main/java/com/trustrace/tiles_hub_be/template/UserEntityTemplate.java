package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserEntityTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void save(UserEntity userEntity) {
        mongoTemplate.save(userEntity);
    }

    public Boolean existsByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Boolean isExists = mongoTemplate.exists(query, UserEntity.class);
        return isExists;
    }

    public Boolean existsByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        Boolean isExists = mongoTemplate.exists(query, UserEntity.class);
        return isExists;
    }

    public Optional<UserEntity> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        UserEntity userEntity = mongoTemplate.findOne(query, UserEntity.class);
        return Optional.ofNullable(userEntity);
    }

    public Optional<UserEntity> findById(String id) {
        UserEntity userEntity  = mongoTemplate.findById(id, UserEntity.class);
        return Optional.ofNullable(userEntity);
    }
}
