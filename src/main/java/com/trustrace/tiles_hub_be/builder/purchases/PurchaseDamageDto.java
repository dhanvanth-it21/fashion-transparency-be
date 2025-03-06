package com.trustrace.tiles_hub_be.builder.purchases;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDamageDto {
    private String _id;
    private String brandName;
    private String purchaseId;
}

