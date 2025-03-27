package com.trustrace.fashion_transparency_be.builder.orders;

import com.trustrace.fashion_transparency_be.model.collections.tiles_list.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewOrderDto {


    private String salesId;

    private String shopId;

    private List<OrderItem> itemList;

    private double damagePercentage;
}
