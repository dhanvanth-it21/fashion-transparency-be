package com.trustrace.fashion_transparency_be.builder.damages;


import com.trustrace.fashion_transparency_be.model.collections.damage.DamageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDamageStatus {
    private String _id;

    private String damageReportId;
    private String skuCode;
    private int qty;
    private DamageStatus status;
}
