package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.model.collections.damage.DamageReport;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.DamageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/damage-report")
public class DamageReportController {

    @Autowired
    private DamageReportService damageReportService;

    @PostMapping
    public ResponseEntity<ApiResponse<DamageReport>> createDamageReport(@RequestBody DamageReport damageReport) {
        DamageReport createdReport = damageReportService.createDamageReport(damageReport);
        return ResponseEntity.ok(ResponseUtil.success("Damage report created successfully", createdReport, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DamageReport>> getDamageReportById(@PathVariable String id) {
        DamageReport damageReport = damageReportService.getDamageReportById(id);
        return ResponseEntity.ok(ResponseUtil.success("Damage report found", damageReport, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DamageReport>>> getAllDamageReports() {
        List<DamageReport> reports = damageReportService.getAllDamageReports();
        return ResponseEntity.ok(ResponseUtil.success("All damage reports retrieved", reports, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DamageReport>> updateDamageReport(@PathVariable String id, @RequestBody DamageReport damageReport) {
        DamageReport updatedReport = damageReportService.updateDamageReport(id, damageReport);
        return ResponseEntity.ok(ResponseUtil.success("Damage report updated successfully", updatedReport, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDamageReport(@PathVariable String id) {
        damageReportService.deleteDamageReport(id);
        return ResponseEntity.ok(ResponseUtil.success("Damage report deleted successfully", null, null));
    }
}
