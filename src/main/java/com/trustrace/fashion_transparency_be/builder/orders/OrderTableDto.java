package com.trustrace.fashion_transparency_be.builder.orders;

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
public class OrderTableDto {
    private String _id;

    private String orderId;

    private String shopName;

    private OrderStatus status;

    private Date createdAt;
}
