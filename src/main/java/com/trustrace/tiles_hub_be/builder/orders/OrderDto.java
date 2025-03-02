package com.trustrace.tiles_hub_be.builder.orders;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderItem;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String _id;

    private String salesId;

    private String retailerShopId;

    private String shopName;

    private OrderStatus status;

    private List<OrderItem> itemList;

    private double damagePercentage;

    private Date createdAt;

    private Date updatedAt;
}
