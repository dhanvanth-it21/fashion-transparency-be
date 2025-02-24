package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.Role;
import com.trustrace.tiles_hub_be.model.UserRole;
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
