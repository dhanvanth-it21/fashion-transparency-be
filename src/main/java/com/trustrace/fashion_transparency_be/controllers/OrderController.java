package com.trustrace.fashion_transparency_be.controllers;

import com.trustrace.fashion_transparency_be.builder.orders.NewOrderDto;
import com.trustrace.fashion_transparency_be.builder.orders.OrderDamageDto;
import com.trustrace.fashion_transparency_be.builder.orders.OrderDetailDto;
import com.trustrace.fashion_transparency_be.builder.orders.OrderTableDto;
import com.trustrace.fashion_transparency_be.model.collections.activityLogs.orderTracking.OrderTracker;
import com.trustrace.fashion_transparency_be.model.collections.tiles_list.Order;
import com.trustrace.fashion_transparency_be.model.collections.tiles_list.OrderStatus;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ApiResponse;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ResponseUtil;
import com.trustrace.fashion_transparency_be.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody NewOrderDto newOrderDto) {
        Order createdOrder = orderService.createOrder(newOrderDto);
        return ResponseEntity.ok(ResponseUtil.success("Order created successfully", createdOrder, null));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDetailDto>> getOrderById(@PathVariable String id) {
        OrderDetailDto orderDto = orderService.getOrderDetailsById(id);
        return ResponseEntity.ok(ResponseUtil.success("Order found", orderDto, null));
    }

    @GetMapping("order-tracker/{id}")
    public ResponseEntity<ApiResponse<OrderTracker>> getOrderTrackerById(@PathVariable String id) {
        OrderTracker orderTracker = orderService.getOrderTrackerById(id);
        return ResponseEntity.ok(ResponseUtil.success("Order Tracker found", orderTracker, null));
    }

    @PutMapping("/status/{id}")
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
    public ResponseEntity<ApiResponse<List<OrderTableDto>>> getAllOrderTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "filterBy", defaultValue = "") OrderStatus orderStatus
            ) {
        Page<OrderTableDto> orderTableDtos = orderService.getAllOrdersTableDetails(page, size, sortBy, sortDirection, search, orderStatus);
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
        return ResponseEntity.ok(ResponseUtil.success("Order fetched", orderTableDtos.getContent(), metadata));
    }

    @GetMapping("search")
    public ResponseEntity<ApiResponse<List<OrderDamageDto>>> searchOrders(
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        List<OrderDamageDto> orderDamageDtos = orderService.searchOrders(search);
        return ResponseEntity.ok(ResponseUtil.success("Orders fetched", orderDamageDtos, null));
    }


}
