package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.builder.orders.OrderTableDto;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderStatus;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(ResponseUtil.success("Order created successfully", createdOrder, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(ResponseUtil.success("Order found", order, null));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(@PathVariable String id, @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(ResponseUtil.success("Order status updated", updatedOrder, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ResponseUtil.success("Order deleted successfully", null, null));
    }



    @GetMapping("table-details")
    public ResponseEntity<ApiResponse<List<OrderTableDto>>> getAllSupplierTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        Page<OrderTableDto> orderTableDtos = orderService.getAllOrdersTableDetails(page, size, sortBy, sortDirection, search);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("pageable", orderTableDtos.getPageable());
        metadata.put("totalElements", orderTableDtos.getTotalElements());
        metadata.put("totalPages", orderTableDtos.getTotalPages());
        metadata.put("isFirst", orderTableDtos.isFirst());
        metadata.put("isLast", orderTableDtos.isLast());
        metadata.put("size", orderTableDtos.getSize());
        metadata.put("number", orderTableDtos.getNumber());
        metadata.put("numberOfElements", orderTableDtos.getNumberOfElements());
        metadata.put("sort", orderTableDtos.getSort());
        return ResponseEntity.ok(ResponseUtil.success("Retailer shops fetched", orderTableDtos.getContent(), metadata));
    }
}
