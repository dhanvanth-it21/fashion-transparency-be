package com.trustrace.tiles_hub_be.builder.retailshop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetailShopDto {


    private String _id;
    private String shopName;
    private String contactPersonName;
    private String email;
    private String phone;
    private String address;
    private double creditNote;
}
