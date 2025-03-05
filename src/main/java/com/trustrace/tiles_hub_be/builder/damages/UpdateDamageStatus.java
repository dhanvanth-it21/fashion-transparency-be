package com.trustrace.tiles_hub_be.builder.damages;


import com.trustrace.tiles_hub_be.model.collections.damage.DamageLocation;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageStatus;
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
    private String skuCode;
    private int qty;
    private DamageStatus status;
}
