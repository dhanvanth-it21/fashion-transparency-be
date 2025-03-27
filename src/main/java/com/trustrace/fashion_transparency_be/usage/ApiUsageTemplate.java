package com.trustrace.fashion_transparency_be.usage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ApiUsageTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(ApiUsage apiUsage) {
        mongoTemplate.save(apiUsage);
    }
}
