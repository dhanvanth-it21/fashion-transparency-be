package com.trustrace.fashion_transparency_be.dao;

import com.trustrace.fashion_transparency_be.model.collections.activityLogs.orderTracking.OrderTracker;
import com.trustrace.fashion_transparency_be.template.OrderTrackerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderTrackerDao {

    @Autowired
    private OrderTrackerTemplate orderTrackerTemplate;

    public void save(OrderTracker orderTracker) {
        orderTrackerTemplate.save(orderTracker);
    }

    public OrderTracker getByOrderId(String orderId) {
        return orderTrackerTemplate.getByOrderId(orderId);

    }
}
