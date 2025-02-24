package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Purchase>> createPurchase(@RequestBody Purchase purchase) {
        Purchase createdPurchase = purchaseService.createPurchase(purchase);
        return ResponseEntity.ok(ResponseUtil.success("Purchase created successfully", createdPurchase, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Purchase>> getPurchaseById(@PathVariable String id) {
        Purchase purchase = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(ResponseUtil.success("Purchase found", purchase, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePurchase(@PathVariable String id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok(ResponseUtil.success("Purchase deleted successfully", null, null));
    }
}
