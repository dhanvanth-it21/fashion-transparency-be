package com.trustrace.fashion_transparency_be.model.collections.tiles_list;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class Order {
    @Id
    private String _id;

    private String orderId; // unique order id

    private String salesId; // it is just a id of sales (not included in this project)

    private String shopId; // referencing the retail shop, the order placed at warehouse

    private String shopName; // shop name of the retailer shop

    private OrderStatus status; // PENDING, PICKING, DISPATCHED, DELIVERED

    private List<OrderItem> itemList;

    private double damagePercentage; //acceptable damage occured during this order delivery


    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
