package com.trustrace.fashion_transparency_be.controllers;

import com.trustrace.fashion_transparency_be.model.collections.activityLogs.InventoryTransaction;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ApiResponse;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ResponseUtil;
import com.trustrace.fashion_transparency_be.service.InventoryTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/inventory-transaction")
public class InventoryTransactionController {

    @Autowired
    private InventoryTransactionService inventoryTransactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryTransaction>> createTransaction(@RequestBody InventoryTransaction transaction) {
        InventoryTransaction createdTransaction = inventoryTransactionService.createTransaction(transaction);
        return ResponseEntity.ok(ResponseUtil.success("Inventory transaction created successfully", createdTransaction, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryTransaction>> getTransactionById(@PathVariable String id) {
        InventoryTransaction transaction = inventoryTransactionService.getTransactionById(id);
        return ResponseEntity.ok(ResponseUtil.success("Inventory transaction found", transaction, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryTransaction>>> getAllTransactions() {
        List<InventoryTransaction> transactions = inventoryTransactionService.getAllTransactions();
        return ResponseEntity.ok(ResponseUtil.success("All inventory transactions retrieved", transactions, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTransaction(@PathVariable String id) {
        inventoryTransactionService.deleteTransaction(id);
        return ResponseEntity.ok(ResponseUtil.success("Inventory transaction deleted successfully", null, null));
    }
}
