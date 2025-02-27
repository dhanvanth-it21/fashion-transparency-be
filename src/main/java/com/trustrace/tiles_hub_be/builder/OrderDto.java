package com.trustrace.tiles_hub_be.builder;


import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String orderID;
    private String retailerName;
    private Date orderDate;
    private OrderStatus status;
}
