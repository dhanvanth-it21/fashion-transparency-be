package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.OrderDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        return orderDao.save(order);
    }

    public Order getOrderById(String id) {
        return orderDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }

    public Order updateOrderStatus(String id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderDao.save(order);
    }

    public void deleteOrder(String id) {
        if (!orderDao.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }
        orderDao.deleteById(id);
    }
}
