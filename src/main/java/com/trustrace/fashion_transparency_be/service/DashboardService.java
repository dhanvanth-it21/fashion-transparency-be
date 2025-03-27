package com.trustrace.fashion_transparency_be.service;

import com.trustrace.fashion_transparency_be.dao.DamageReportDao;
import com.trustrace.fashion_transparency_be.dao.DashboardDao;
import com.trustrace.fashion_transparency_be.dao.OrderDao;
import com.trustrace.fashion_transparency_be.dao.TileDao;
import com.trustrace.fashion_transparency_be.model.collections.dashboard.EmployeeOverviewMetrics;
import com.trustrace.fashion_transparency_be.model.collections.dashboard.OverviewMetrics;
import com.trustrace.fashion_transparency_be.model.collections.dashboard.UnderReviewDamageMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private DashboardDao dashboardDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DamageReportDao damageReportDao;

    @Autowired
    private TileDao tileDao;

    public OverviewMetrics getOverviewMetrics() {
        int totalInventoryItems = getTotalInventoryItem();
        int totalPendingOrders = getTotalPendingOrders();
        int totalOrders = getTotalOrders();
        int unseenDamagesReported = getToatalUnseenDamagesReported();

        return OverviewMetrics.builder()
                .totalInventoryItems(totalInventoryItems)
                .totalPendingOrders(totalPendingOrders)
                .totalOrders(totalOrders)
                .unseenDamagesReported(unseenDamagesReported)
                .build();
    }

    private int getToatalUnseenDamagesReported() {
        return damageReportDao.getTotalUnseenDamagesReported();
    }

    private int getTotalOrders() {
        return orderDao.getTotalOrders();
    }

    private int getTotalPendingOrders() {
        return orderDao.getTotalPendingOrders();
    }

    private int getTotalInventoryItem() {
        return dashboardDao.getTotalInventoryItem();
    }

    public UnderReviewDamageMetrics getDamageMetrics() {
        int warehouse = damageReportDao.getTotalUnseenDamagesReportedForWarehouse();
        int retailShop = damageReportDao.getTotalUnseenDamagesReportedForRetailShop();
        int manufacturer = damageReportDao.getTotalUnseenDamagesReportedForManufacturer();

        return UnderReviewDamageMetrics.builder()
                .warehouse(warehouse)
                .retailShop(retailShop)
                .manufacturer(manufacturer)
                .build();
    }

    public Integer getTotalLowStocks() {
        return tileDao.getTotalLowStocks();
    }

    public EmployeeOverviewMetrics getEmployeeOverviewMetrics() {
        return EmployeeOverviewMetrics.builder()
                .totalInventoryItems(dashboardDao.getTotalInventoryItem())
                .totalPickingOrders(orderDao.getTotalPickingOrders())
                .totalDamageReports(damageReportDao.getTotalDamageReports(getAuthenticatedUserEmail()))
                .approvedDamagedReports(damageReportDao.getTotalApprovedDamageReports(getAuthenticatedUserEmail()))
                .rejectedDamagedReports(damageReportDao.getTotalRejectedDamageReports(getAuthenticatedUserEmail()))
                .underReviewDamagedReports(damageReportDao.getTotalUnderReviewDamageReports(getAuthenticatedUserEmail()))
                .build();
    }


    private String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }
}
