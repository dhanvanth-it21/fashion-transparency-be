package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.model.collections.damage.DamageReport;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageStatus;
import com.trustrace.tiles_hub_be.template.DamageReportTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DamageReportDao {

    @Autowired
    private DamageReportTemplate damageReportTemplate;

    public DamageReport save(DamageReport damageReport) {
        return damageReportTemplate.save(damageReport);
    }

    public Optional<DamageReport> findById(String id) {
        return damageReportTemplate.findById(id);
    }

    public List<DamageReport> findAll() {
        return damageReportTemplate.findAll();
    }

    public boolean existsById(String id) {
        return damageReportTemplate.existsById(id);
    }

    public void deleteById(String id) {
        damageReportTemplate.deleteById(id);
    }

    public List<DamageReport> findByStatus(DamageStatus underReview) {
        return damageReportTemplate.findByStatus(underReview);
    }

    public Page<DamageReport> getAllDamageReports(int page, int size, String sortBy, String sortDirection, String search, DamageStatus filterBy) {
        return damageReportTemplate.getAllDamageReports(page, size, sortBy, sortDirection, search, filterBy);
    }

    public List<DamageReport> findByOrderId(String orderId) {
        return damageReportTemplate.findByOrderId(orderId);
    }

    public Optional<DamageReport> findByTileIdAndOrderId(String id, String orderId) {
        return damageReportTemplate.findByTileIdAndOrderId(id, orderId);
    }

    public int getTotalUnseenDamagesReported() {
        return damageReportTemplate.getTotalUnderReviewDamageReports();
    }

    public int getTotalUnseenDamagesReportedForWarehouse() {
        return damageReportTemplate.getTotalUnderReviewDamageReportsForWarehouse();
    }

    public int getTotalUnseenDamagesReportedForRetailShop() {
        return damageReportTemplate.getTotalUnderReviewDamageReportsForRetailShop();
    }

    public int getTotalUnseenDamagesReportedForManufacturer() {
        return damageReportTemplate.getTotalUnderReviewDamageReportsForManufacturer();
    }
}
