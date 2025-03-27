package com.trustrace.fashion_transparency_be.dao;

import com.trustrace.fashion_transparency_be.template.DashboardTemplate;
import com.trustrace.fashion_transparency_be.template.TileTemplate;
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
