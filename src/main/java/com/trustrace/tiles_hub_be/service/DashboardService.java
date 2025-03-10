package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.DamageReportDao;
import com.trustrace.tiles_hub_be.dao.DashboardDao;
import com.trustrace.tiles_hub_be.dao.OrderDao;
import com.trustrace.tiles_hub_be.model.collections.dashboard.OverviewMetrics;
import com.trustrace.tiles_hub_be.model.collections.dashboard.UnderReviewDamageMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private DashboardDao dashboardDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DamageReportDao damageReportDao;

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
}
