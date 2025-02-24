package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.model.collections.activityLogs.InventoryTransaction;
import com.trustrace.tiles_hub_be.template.InventoryTransactionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryTransactionDao {


    @Autowired
    private InventoryTransactionTemplate inventoryTransactionTemplate;

    public InventoryTransaction save(InventoryTransaction transaction) {
        return inventoryTransactionTemplate.save(transaction);
    }

    public Optional<InventoryTransaction> findById(String id) {
        return inventoryTransactionTemplate.findById(id);
    }

    public List<InventoryTransaction> findAll() {
        return inventoryTransactionTemplate.findAll();
    }

    public boolean existsById(String id) {
        return inventoryTransactionTemplate.existsById(id);
    }

    public void deleteById(String id) {
        inventoryTransactionTemplate.deleteById(id);
    }
}
