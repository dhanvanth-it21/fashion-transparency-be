package com.trustrace.tiles_hub_be.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;
}
