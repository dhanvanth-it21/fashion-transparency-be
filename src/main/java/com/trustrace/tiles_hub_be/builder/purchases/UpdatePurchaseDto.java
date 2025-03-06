package com.trustrace.tiles_hub_be.builder.purchases;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePurchaseDto {

    private String _id;


    private String purchaseId;

    private String brandName;

    private PurchaseStatus status;

    private String approvedByUserId;
}
