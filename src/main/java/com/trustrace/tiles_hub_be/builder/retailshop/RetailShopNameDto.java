package com.trustrace.tiles_hub_be.builder.retailshop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetailShopNameDto {

    private String _id;
    private String shopName;

}
