package com.trustrace.fashion_transparency_be.builder;


import com.trustrace.fashion_transparency_be.model.collections.tiles_list.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private String orderID;
    private String retailerName;
    private Date orderDate;
    private OrderStatus status;
}
