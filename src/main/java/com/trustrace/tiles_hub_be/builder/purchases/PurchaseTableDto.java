package com.trustrace.tiles_hub_be.builder.purchases;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseItem;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseStatus;
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
public class PurchaseTableDto {

    private String _id;

    private String purchaseId;

    private String brandName;

//    private String recordedByUserId;

    private String recordedBy;

    private Date createdAt;

    private PurchaseStatus status;
}
