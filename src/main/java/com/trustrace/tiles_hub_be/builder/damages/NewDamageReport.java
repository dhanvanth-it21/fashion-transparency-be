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
public class NewDamageReport {
    private String tileSku;
    private String retailerId;
    private DamageLocation damageLocation;
    private int qty;
    private String remark;
}
