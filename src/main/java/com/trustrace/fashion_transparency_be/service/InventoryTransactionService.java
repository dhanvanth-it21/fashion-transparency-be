package com.trustrace.fashion_transparency_be.service;

import com.trustrace.fashion_transparency_be.dao.InventoryTransactionDao;
import com.trustrace.fashion_transparency_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.fashion_transparency_be.model.collections.activityLogs.InventoryTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryTransactionService {

    @Autowired
    private InventoryTransactionDao inventoryTransactionDao;


    public InventoryTransaction createTransaction(InventoryTransaction transaction) {
        return inventoryTransactionDao.save(transaction);
    }

    public InventoryTransaction getTransactionById(String id) {
        return inventoryTransactionDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory transaction not found with ID: " + id));
    }

    public List<InventoryTransaction> getAllTransactions() {
        return inventoryTransactionDao.findAll();
    }

    public void deleteTransaction(String id) {
        if (!inventoryTransactionDao.existsById(id)) {
            throw new ResourceNotFoundException("Inventory transaction not found with ID: " + id);
        }
        inventoryTransactionDao.deleteById(id);
    }
}
