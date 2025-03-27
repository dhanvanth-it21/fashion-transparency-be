package com.trustrace.fashion_transparency_be.builder.orders;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDamageDto {
    private String _id;
    private String shopName;
    private String orderId;
}
