package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RetailerShopTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public RetailerShop save(RetailerShop retailerShop) {
        return mongoTemplate.save(retailerShop);
    }

    public Optional<RetailerShop> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, RetailerShop.class));
    }

    public List<RetailerShop> findAll() {
        return mongoTemplate.findAll(RetailerShop.class);
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), RetailerShop.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), RetailerShop.class);
    }
}
