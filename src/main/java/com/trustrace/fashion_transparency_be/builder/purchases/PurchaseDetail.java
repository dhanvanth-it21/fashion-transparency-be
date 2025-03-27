package com.trustrace.fashion_transparency_be.builder.purchases;

import com.trustrace.fashion_transparency_be.model.collections.tiles_list.PurchaseStatus;
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
public class PurchaseDetail {
    private String purchaseId;
    private String brandName;
    private List<ItemListDetails> itemList;
    private double damagePercentage;
    private String recordedBy;
    private String approvedBy;
    private PurchaseStatus status;
    private Date createdAt;
}
