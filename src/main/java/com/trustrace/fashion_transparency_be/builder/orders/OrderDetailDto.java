package com.trustrace.fashion_transparency_be.builder.orders;

import com.trustrace.fashion_transparency_be.builder.purchases.ItemListDetails;
import com.trustrace.fashion_transparency_be.model.collections.tiles_list.OrderStatus;
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
public class OrderDetailDto {

    private String _id;

    private String orderId;

    private String salesId;

    private String retailerShopId;

    private String shopName;

    private OrderStatus status;

    private List<ItemListDetails> itemList;

    private double damagePercentage;

    private Date createdAt;

    private Date updatedAt;
}
