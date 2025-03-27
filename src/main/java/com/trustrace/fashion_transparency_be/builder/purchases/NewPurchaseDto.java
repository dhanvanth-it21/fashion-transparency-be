package com.trustrace.fashion_transparency_be.builder.purchases;

import com.trustrace.fashion_transparency_be.model.collections.tiles_list.PurchaseItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewPurchaseDto {

    private String purchaseId;

    private String supplierId;

    private List<PurchaseItem> itemList;

    private double damagePercentage;

    private String recordedByUserName;

}
