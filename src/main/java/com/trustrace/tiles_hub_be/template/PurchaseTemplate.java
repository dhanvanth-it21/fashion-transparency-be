package com.trustrace.tiles_hub_be.template;


import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PurchaseTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Purchase save(Purchase purchase) {
        return mongoTemplate.save(purchase);
    }

    public Optional<Purchase> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Purchase.class));
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), Purchase.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Purchase.class);
    }
}
