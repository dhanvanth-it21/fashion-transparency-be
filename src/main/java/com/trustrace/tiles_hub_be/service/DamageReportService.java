package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.damages.DamageReportDto;
import com.trustrace.tiles_hub_be.builder.damages.NewDamageReport;
import com.trustrace.tiles_hub_be.dao.DamageReportDao;
import com.trustrace.tiles_hub_be.dao.TileDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageLocation;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageReport;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageStatus;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageReportService {

    @Autowired
    private DamageReportDao damageReportDao;


    @Autowired
    private TileDao tileDao;

    public DamageReport createDamageReport(NewDamageReport newDamageReport) {
        DamageReport damageReport = DamageReport.builder()
                .tileId(tileDao.findBySkuCode(newDamageReport.getTileSku()).get_id())
                .damageLocation(newDamageReport.getDamageLocation())
                .qty(newDamageReport.getQty())
                .remark(newDamageReport.getRemark())
                .status(DamageStatus.UNDER_REVIEW)
                .build();

        if(damageReport.getDamageLocation() == DamageLocation.TO_RETAIL_SHOP) {
            damageReport.setRetailerId(newDamageReport.getRetailerId());
        }
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

    public DamageReport reportDamage(DamageReport report) {
        report.setStatus(DamageStatus.UNDER_REVIEW);
        return damageReportDao.save(report);
    }

    public List<DamageReport> getPendingReports() {
        return damageReportDao.findByStatus(DamageStatus.UNDER_REVIEW);
    }

    public void approveReport(String id, String approvedByUserId) {
        DamageReport report = damageReportDao.findById(id).orElseThrow();
        report.setStatus(DamageStatus.APPROVED);
        report.setApprovedByUserId(approvedByUserId);
        damageReportDao.save(report);

        Tile tile = tileDao.findById(report.getTileId());
        tile.setQty(tile.getQty() - report.getQty());
        tileDao.save(tile);
    }

    public void rejectReport(String id, String reason) {
        DamageReport report = damageReportDao.findById(id).orElseThrow();
        report.setStatus(DamageStatus.REJECTED);
        report.setRemark(reason);
        damageReportDao.save(report);
    }


    public Page<DamageReportDto> getAllDamageReportsTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Page<DamageReport> paginated = damageReportDao.getAllDamageReports(page, size, sortBy, sortDirection, search);
        List<DamageReport> reports = paginated.getContent();
        List<DamageReportDto> reportDtos = reports.stream()
                .map(report -> DamageReportDto.builder()
                        ._id(report.get_id())
                        .tileSku(tileDao.findById(report.getTileId()).getSkuCode())
                        .damageLocation(report.getDamageLocation())
                        .qty(report.getQty())
                        .status(report.getStatus())
                        .reportedByUserId(report.getReportedByUserId())
                        .build())
                .toList();
        return new PageImpl<>(reportDtos, paginated.getPageable(), paginated.getTotalElements());
    }
}
