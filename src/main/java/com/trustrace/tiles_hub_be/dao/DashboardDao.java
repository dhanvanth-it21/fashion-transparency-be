package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.template.DashboardTemplate;
import com.trustrace.tiles_hub_be.template.TileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardDao {

    @Autowired
    private DashboardTemplate dashboardTemplate;

    @Autowired
    private TileTemplate tileTemplate;

    public int getTotalInventoryItem() {
        return tileTemplate.getTotalTileCount();
    }
}
