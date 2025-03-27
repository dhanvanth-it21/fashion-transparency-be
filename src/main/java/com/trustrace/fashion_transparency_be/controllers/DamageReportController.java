package com.trustrace.fashion_transparency_be.controllers;

import com.trustrace.fashion_transparency_be.builder.damages.DamageReportDto;
import com.trustrace.fashion_transparency_be.builder.damages.NewDamageReport;
import com.trustrace.fashion_transparency_be.builder.damages.UpdateDamageStatus;
import com.trustrace.fashion_transparency_be.model.collections.damage.DamageReport;
import com.trustrace.fashion_transparency_be.model.collections.damage.DamageStatus;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ApiResponse;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ResponseUtil;
import com.trustrace.fashion_transparency_be.service.DamageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/damage-report")
public class DamageReportController {

    @Autowired
    private DamageReportService damageReportService;

    @PostMapping
    public ResponseEntity<ApiResponse<DamageReport>> createDamageReport(@RequestBody NewDamageReport newDamageReport) {
        DamageReport createdReport = damageReportService.createDamageReport(newDamageReport);
        return ResponseEntity.ok(ResponseUtil.success("Damage report created successfully", createdReport, null));
    }





    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DamageReportDto>> getDamageReportById(@PathVariable String id) {
        DamageReportDto damageReportDto = damageReportService.getDamageReportDetailById(id);
        return ResponseEntity.ok(ResponseUtil.success("Damage report found", damageReportDto, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DamageReport>>> getAllDamageReports() {
        List<DamageReport> reports = damageReportService.getAllDamageReports();
        return ResponseEntity.ok(ResponseUtil.success("All damage reports retrieved", reports, null));
    }
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<DamageReport>>> getPendingReports() {
        List<DamageReport> reports = damageReportService.getPendingReports();
        return ResponseEntity.ok(ResponseUtil.success("All pending damage reports retrieved", reports, null));
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

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<String>> approveReport(@PathVariable String id) {
        damageReportService.approveReport(id);
        return ResponseEntity.ok(ResponseUtil.success("Damage report approved successfully", "Damage report approved successfully", null));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<String>> rejectReport(@PathVariable String id) {
        damageReportService.rejectReport(id);
        return ResponseEntity.ok(ResponseUtil.success("Damage report rejected successfully", "Damage report rejected successfully", null));
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity<ApiResponse<UpdateDamageStatus>> getStatusById(@PathVariable String id) {
        UpdateDamageStatus updateDamageStatus = damageReportService.getStatusById(id);
        return ResponseEntity.ok(ResponseUtil.success("Fetched Damage stats successfully", updateDamageStatus, null));
    }

    @GetMapping("/table-details")
    public ResponseEntity<ApiResponse<List<DamageReportDto>>> getAllDamageReportsTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "filterBy", defaultValue = "") DamageStatus filterBy
    ) {
        Page<DamageReportDto> damageReportDtos = damageReportService.getAllDamageReportsTableDetails(page, size, sortBy, sortDirection, search, filterBy);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("pageable", damageReportDtos.getPageable());
        metadata.put("totalElements", damageReportDtos.getTotalElements());
        metadata.put("totalPages", damageReportDtos.getTotalPages());
        metadata.put("isFirst", damageReportDtos.isFirst());
        metadata.put("isLast", damageReportDtos.isLast());
        metadata.put("size", damageReportDtos.getSize());
        metadata.put("number", damageReportDtos.getNumber());
        metadata.put("numberOfElements", damageReportDtos.getNumberOfElements());
        metadata.put("sort", damageReportDtos.getSort());

        return ResponseEntity.ok(ResponseUtil.success("Damage Reports Fetched", damageReportDtos.getContent(), metadata));
    }
}
