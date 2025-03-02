package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.template.OrderTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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

    public Page<Order> getAllOrders(int page, int size, String sortBy, String sortDirection, String search) {
        return orderTemplate.findAll(page, size, sortBy, sortDirection, search);
    }
}
