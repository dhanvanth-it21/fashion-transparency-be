package com.trustrace.fashion_transparency_be.builder.damages;

import com.trustrace.fashion_transparency_be.model.collections.damage.DamageLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewDamageReport {
    private String skuCode;
    private String orderId;
    private String purchaseId;
    private DamageLocation damageLocation;
    private int qty;
    private String remark;
}
