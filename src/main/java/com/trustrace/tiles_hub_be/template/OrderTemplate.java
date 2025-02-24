package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Order save(Order order) {
        return mongoTemplate.save(order);
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Order.class));
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), Order.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Order.class);
    }
}
