package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.orders.OrderTableDto;
import com.trustrace.tiles_hub_be.builder.suppier.SupplierTableDto;
import com.trustrace.tiles_hub_be.dao.OrderDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.Actor.Supplier;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<OrderTableDto> getAllOrdersTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Page<Order> paginated = orderDao.getAllOrders(page, size, sortBy, sortDirection, search);
        List<Order> orders = paginated.getContent();
        List<OrderTableDto> orderTableDtos = orders.stream()
                .map(order -> OrderTableDto.builder()
                        ._id(order.get_id())
                        .createdAt(order.getCreatedAt())
                        .status(order.getStatus())
                        .shopName(order.getShopName())
                        .build())
                .toList();

        return new PageImpl<>(orderTableDtos, paginated.getPageable(), paginated.getTotalElements());
    }
}
