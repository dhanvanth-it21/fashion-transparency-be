package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.DamageReportDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageReportService {

    @Autowired
    private DamageReportDao damageReportDao;

    public DamageReport createDamageReport(DamageReport damageReport) {
        return damageReportDao.save(damageReport);
    }

    public DamageReport getDamageReportById(String id) {
        return damageReportDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Damage report not found with ID: " + id));
    }

    public List<DamageReport> getAllDamageReports() {
        return damageReportDao.findAll();
    }

    public DamageReport updateDamageReport(String id, DamageReport updatedReport) {
        DamageReport existingReport = getDamageReportById(id);
        existingReport.setTileId(updatedReport.getTileId());
        existingReport.setDamageLocation(updatedReport.getDamageLocation());
        existingReport.setQty(updatedReport.getQty());
        existingReport.setRemark(updatedReport.getRemark());
        existingReport.setStatus(updatedReport.getStatus());
        existingReport.setReportedByUserId(updatedReport.getReportedByUserId());
        existingReport.setApprovedByUserId(updatedReport.getApprovedByUserId());
        return damageReportDao.save(existingReport);
    }

    public void deleteDamageReport(String id) {
        if (!damageReportDao.existsById(id)) {
            throw new ResourceNotFoundException("Damage report not found with ID: " + id);
        }
        damageReportDao.deleteById(id);
    }
}
