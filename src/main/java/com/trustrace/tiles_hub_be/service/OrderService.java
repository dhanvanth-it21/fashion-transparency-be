package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.orders.NewOrderDto;
import com.trustrace.tiles_hub_be.builder.orders.OrderDamageDto;
import com.trustrace.tiles_hub_be.builder.orders.OrderDetailDto;
import com.trustrace.tiles_hub_be.builder.orders.OrderTableDto;
import com.trustrace.tiles_hub_be.builder.purchases.ItemListDetails;
import com.trustrace.tiles_hub_be.dao.OrderDao;
import com.trustrace.tiles_hub_be.dao.RetailerShopDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderItem;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RetailerShopDao retailerShopDao;

    @Autowired
    private TileService tileService;

    public Order createOrder(NewOrderDto newOrderDto) {
        String orderId = generateOrderId();
       Order order = Order.builder()
                .orderId(orderId)
               .shopId(newOrderDto.getShopId())
               .salesId(newOrderDto.getSalesId())
               .damagePercentage(newOrderDto.getDamagePercentage())
               .itemList(newOrderDto.getItemList())
               .status(OrderStatus.PENDING)
               .shopName(retailerShopDao.getShopNameByid(newOrderDto.getShopId()))
               .build();

        Order savedOrder = orderDao.save(order);
        tileService.updateStockByOrderItems(order.getItemList());
        return savedOrder;
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

    public Page<OrderTableDto> getAllOrdersTableDetails(int page, int size, String sortBy, String sortDirection, String search, OrderStatus orderStatus) {
        Page<Order> paginated = orderDao.getAllOrders(page, size, sortBy, sortDirection, search, orderStatus);
        List<Order> orders = paginated.getContent();
        List<OrderTableDto> orderTableDtos = orders.stream()
                .map(order -> OrderTableDto.builder()
                        ._id(order.get_id())
                        .orderId(order.getOrderId())
                        .createdAt(order.getCreatedAt())
                        .status(order.getStatus())
                        .shopName(order.getShopName())
                        .build())
                .toList();

        return new PageImpl<>(orderTableDtos, paginated.getPageable(), paginated.getTotalElements());
    }

    public String generateOrderId() {
        return "ORD-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
    }

    public List<OrderDamageDto> searchOrders(String search) {
        List<Order> orders = orderDao.searchOrders(search);
        return orders.stream()
                .map(order -> OrderDamageDto.builder()
                        ._id(order.get_id())
                        .orderId(order.getOrderId())
                        .shopName(order.getShopName())
                        .build())
                .toList();

    }

    public OrderDetailDto getOrderDetailsById(String id) {
        Order order = orderDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("no orders found with the provided id"));
        return OrderDetailDto.builder()
                .retailerShopId(order.getShopId())
                .salesId(order.getSalesId())
                .shopName(order.getShopName())
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .updatedAt(order.getUpdatedAt())
                .createdAt(order.getCreatedAt())
                .itemList(getItemListDetails(order.getItemList()))
                .build();
    }

    private List<ItemListDetails> getItemListDetails(List<OrderItem> itemLists) {
        return itemLists.stream()
                .map(itemList -> ItemListDetails.builder()
                        .qty(itemList.getRequiredQty())
                        .skuCode(tileService.getTileById(itemList.getTileId()).getSkuCode())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
