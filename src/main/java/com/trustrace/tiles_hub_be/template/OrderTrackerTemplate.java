package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.activityLogs.orderTracking.OrderTracker;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class OrderTrackerTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(OrderTracker orderTracker) {
        mongoTemplate.save(orderTracker);
    }

    public OrderTracker getByOrderId(String orderId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("orderId").is(orderId));
        return mongoTemplate.findOne(query, OrderTracker.class);
    }
}
