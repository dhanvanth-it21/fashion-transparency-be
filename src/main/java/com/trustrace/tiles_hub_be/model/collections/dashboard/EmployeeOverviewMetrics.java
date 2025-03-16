package com.trustrace.tiles_hub_be.model.collections.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeOverviewMetrics {
    private int totalInventoryItems;
    private int totalPickingOrders;
    private int totalDamageReports;
    private int approvedDamagedReports;
    private int rejectedDamagedReports;
    private int underReviewDamagedReports;
}
