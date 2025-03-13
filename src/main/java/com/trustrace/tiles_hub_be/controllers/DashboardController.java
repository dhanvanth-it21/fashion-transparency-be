package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.model.collections.dashboard.OverviewMetrics;
import com.trustrace.tiles_hub_be.model.collections.dashboard.UnderReviewDamageMetrics;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/overview-metrics")
    public ResponseEntity<ApiResponse<OverviewMetrics>> getOverviewMetrics() {
        OverviewMetrics overviewMetrics = dashboardService.getOverviewMetrics();
        return ResponseEntity.ok(ResponseUtil.success("Overview Metrics Data fetched", overviewMetrics, null));
    }

    @GetMapping("/damage-metrics")
    public ResponseEntity<ApiResponse<UnderReviewDamageMetrics>> getDamageMetrics() {
        UnderReviewDamageMetrics underReviewDamageMetrics = dashboardService.getDamageMetrics();
        return ResponseEntity.ok(ResponseUtil.success("Damage Metrics Data fetched", underReviewDamageMetrics, null));
    }

    @GetMapping("/total-low-stock")
    public ResponseEntity<ApiResponse<Integer>> getTotalLowStocks() {
        Integer totalLowStocks = dashboardService.getTotalLowStocks();
        return ResponseEntity.ok(ResponseUtil.success("Total low stock fetched", totalLowStocks, null));
    }

}
