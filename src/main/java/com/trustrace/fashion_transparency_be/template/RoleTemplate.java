package com.trustrace.fashion_transparency_be.template;

import com.trustrace.fashion_transparency_be.model.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class RoleTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<Role> findByName(String userRole) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(userRole));
        Role role = mongoTemplate.findOne(query, Role.class);
        return Optional.ofNullable(role);
    }

}
