package com.trustrace.tiles_hub_be.model.collections.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverviewMetrics {
    private int totalInventoryItems;
    private int totalPendingOrders;
    private int totalOrders;
    private int unseenDamagesReported;
}
