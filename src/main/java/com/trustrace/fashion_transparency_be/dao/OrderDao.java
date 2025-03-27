package com.trustrace.fashion_transparency_be.dao;

import com.trustrace.fashion_transparency_be.model.collections.tiles_list.Order;
import com.trustrace.fashion_transparency_be.model.collections.tiles_list.OrderStatus;
import com.trustrace.fashion_transparency_be.template.OrderTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDao {

    @Autowired
    private OrderTemplate orderTemplate;

    public Order save(Order order) {
        return orderTemplate.save(order);
    }

    public Optional<Order> findById(String id) {
        return orderTemplate.findById(id);
    }

    public boolean existsById(String id) {
        return orderTemplate.existsById(id);
    }

    public void deleteById(String id) {
        orderTemplate.deleteById(id);
    }

    public Page<Order> getAllOrders(int page, int size, String sortBy, String sortDirection, String search, OrderStatus orderStatus) {
        return orderTemplate.findAll(page, size, sortBy, sortDirection, search, orderStatus);
    }

    public List<Order> searchOrders(String search) {
        return orderTemplate.searchOrders(search);
    }

    public Optional<Order> findByOrderId(String givenId) {
        return orderTemplate.findByOrderId(givenId);
    }

    public int getTotalPendingOrders() {
        return orderTemplate.getTotalPendingOrders();
    }

    public int getTotalPickingOrders() {
        return orderTemplate.getTotalPickingOrders();
    }

    public int getTotalOrders() {
        return orderTemplate.getTotalOrders();
    }
}
