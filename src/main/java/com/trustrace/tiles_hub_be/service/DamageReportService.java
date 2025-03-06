package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.damages.DamageReportDto;
import com.trustrace.tiles_hub_be.builder.damages.NewDamageReport;
import com.trustrace.tiles_hub_be.builder.damages.UpdateDamageStatus;
import com.trustrace.tiles_hub_be.dao.DamageReportDao;
import com.trustrace.tiles_hub_be.dao.OrderDao;
import com.trustrace.tiles_hub_be.dao.RetailerShopDao;
import com.trustrace.tiles_hub_be.dao.TileDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceAlreadyExistsException;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageLocation;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageReport;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageStatus;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class DamageReportService {

    @Autowired
    private DamageReportDao damageReportDao;


    @Autowired
    private TileDao tileDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RetailerShopDao retailerShopDao;

    @Autowired
    private UserEntityService userEntityService;

    public DamageReport createDamageReport(NewDamageReport newDamageReport) {

        Optional<DamageReport> existingReport = damageReportDao.findByTileIdAndOrderId(
                tileDao.findBySkuCode(newDamageReport.getSkuCode()).get_id(), newDamageReport.getOrderId());
        if (existingReport.isPresent()) {
            throw new ResourceAlreadyExistsException("Damage report already exists for this tile and order");
        }

        String damageReportId = getDamageReportId();
        Tile tile = tileDao.findBySkuCode(newDamageReport.getSkuCode());
        DamageReport damageReport = DamageReport.builder()
                .tileId(tileDao.findBySkuCode(newDamageReport.getSkuCode()).get_id())
                .damageReportId(damageReportId)
                .damageLocation(newDamageReport.getDamageLocation())
                .qty(newDamageReport.getQty())
                .remark(newDamageReport.getRemark())
                .status(DamageStatus.UNDER_REVIEW)
                .reportedByUserId("67bbfe2f8d85f862f666bb10")
                .build();

        if(damageReport.getDamageLocation() == DamageLocation.TO_RETAIL_SHOP) {
            damageReport.setOrderId(newDamageReport.getOrderId());
        }
        else if(damageReport.getDamageLocation() == DamageLocation.FROM_MANUFACTURER) {
            damageReport.setPurchaseId(newDamageReport.getPurchaseId());
            tile.setQty(tile.getQty() - damageReport.getQty());
            tileDao.save(tile);
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

    public void approveReport(String id) {
        DamageReport report = damageReportDao.findById(id).orElseThrow();
        if(report.getStatus() != DamageStatus.UNDER_REVIEW) {
            throw new ResourceNotFoundException("This has been aldready reviewed");
        }
        report.setStatus(DamageStatus.APPROVED);
        report.setApprovedByUserId("67bbfe2f8d85f862f666bb10");
        damageReportDao.save(report);


        if (report.getDamageLocation() == DamageLocation.TO_RETAIL_SHOP) {
            handleRetailShopDamage(report);
        } else if (report.getDamageLocation() != DamageLocation.FROM_MANUFACTURER) {
            Tile tile = tileDao.findById(report.getTileId());
            tile.setQty(tile.getQty() - report.getQty());
            tileDao.save(tile);
        }
    }

    private void handleRetailShopDamage(DamageReport report) {
        Order order = orderDao.findByOrderId(report.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        int totalQtySold = 0;
        for (OrderItem item : order.getItemList()) {
            totalQtySold += item.getRequiredQty();
        }
        List<DamageReport> damageReports = damageReportDao.findByOrderId(report.getOrderId());
        int totalDamagedQty = 0;
        for (DamageReport damageReport : damageReports) {
            totalDamagedQty += damageReport.getQty();
        }
        double damagePercentage = (double) totalDamagedQty / totalQtySold * 100;

        if (damagePercentage > order.getDamagePercentage()) {
            int compensationAmount = totalDamagedQty * 200;
            System.out.println("Compensation amount: " + compensationAmount);
            updateRetailShopCreditNote(order.getShopId(), compensationAmount);
        }
    }

    private void updateRetailShopCreditNote(String shopId, int amount) {
        RetailerShop shop = retailerShopDao.findById(shopId).orElseThrow(() -> new ResourceNotFoundException("Retail shop not found"));
        shop.setCreditNote(shop.getCreditNote() + amount);
        retailerShopDao.save(shop);
    }



    public void rejectReport(String id) {
        DamageReport report = damageReportDao.findById(id).orElseThrow();
        if(report.getStatus() != DamageStatus.UNDER_REVIEW) {
            throw new ResourceNotFoundException("This has been aldready reviewed");
        }
        report.setStatus(DamageStatus.REJECTED);
        report.setRemark("need to be handled later");
        damageReportDao.save(report);

        if (report.getDamageLocation() == DamageLocation.FROM_MANUFACTURER) {
            Tile tile = tileDao.findById(report.getTileId());
            tile.setQty(tile.getQty() + report.getQty());
            tileDao.save(tile);
        }
    }


    public Page<DamageReportDto> getAllDamageReportsTableDetails(int page, int size, String sortBy, String sortDirection, String search, DamageStatus filterBy) {
        Page<DamageReport> paginated = damageReportDao.getAllDamageReports(page, size, sortBy, sortDirection, search, filterBy);
        List<DamageReport> reports = paginated.getContent();
        List<DamageReportDto> reportDtos = reports.stream()
                .map(report -> DamageReportDto.builder()
                        ._id(report.get_id())
                        .damageReportId(report.getDamageReportId())
                        .skuCode(tileDao.findById(report.getTileId()).getSkuCode())
                        .damageLocation(report.getDamageLocation())
                        .qty(report.getQty())
                        .status(report.getStatus())
                        .reportedByUserId(report.getReportedByUserId())
                        .reportedByUserName(userEntityService.findById(report.getReportedByUserId()).getName())
                        .approvedByUserName(getApprovedByUserName(report.getApprovedByUserId()))
                        .build())
                .toList();
        return new PageImpl<>(reportDtos, paginated.getPageable(), paginated.getTotalElements());
    }

    private String getApprovedByUserName(String approvedByUserId) {
        if(approvedByUserId == null) {
            return "Not yet approved";
        }
        else  {
            return userEntityService.findById(approvedByUserId).getName();
        }
    }

    public UpdateDamageStatus getStatusById(String id) {
        DamageReport damageReport = damageReportDao.findById(id).orElseThrow();
        UpdateDamageStatus updateDamageStatus = UpdateDamageStatus.builder()
                .status(damageReport.getStatus())
                ._id(damageReport.get_id())
                .qty(damageReport.getQty())
                .skuCode(tileDao.findById(damageReport.getTileId()).getSkuCode())
                .build();

        return updateDamageStatus;
    }

    public String getDamageReportId() {
        return "DR" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
    }

    public DamageReportDto getDamageReportDetailById(String id) {
        DamageReport damageReport = damageReportDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("No reports found with the given Id"));
        return DamageReportDto.builder()
                .reportedByUserId(damageReport.getReportedByUserId())
                .reportedByUserName(userEntityService.findById(damageReport.getReportedByUserId()).getName())
                .damageReportId(damageReport.getDamageReportId())
                .damageReportId(damageReport.get_id())
                .damageLocation(damageReport.getDamageLocation())
                .qty(damageReport.getQty())
                .status(damageReport.getStatus())
                .skuCode(tileDao.findById(damageReport.getTileId()).getSkuCode())
                .approvedByUserName(getApprovedByUserName(damageReport.getApprovedByUserId()))
                .remark(damageReport.getRemark())
                .build();
    }
}
