package com.trustrace.tiles_hub_be.builder.purchases;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseItem;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    private String recordedByUserName;
    private String approvedByUserName;
    private PurchaseStatus status;
    private Date createdAt;
}
