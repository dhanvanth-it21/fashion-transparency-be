package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.builder.purchases.NewPurchaseDto;
import com.trustrace.tiles_hub_be.builder.purchases.PurchaseTableDto;
import com.trustrace.tiles_hub_be.builder.purchases.UpdatePurchaseDto;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Purchase>> createPurchase(@RequestBody NewPurchaseDto newPurchaseDto) {
        Purchase createdPurchase = purchaseService.createPurchase(newPurchaseDto);
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

    @GetMapping("table-details")
    public ResponseEntity<ApiResponse<List<PurchaseTableDto>>> getAllPurchaseTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        Page<PurchaseTableDto> purchaseTableDtos = purchaseService.getAllOrdersTableDetails(page, size, sortBy, sortDirection, search);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("pageable", purchaseTableDtos.getPageable());
        metadata.put("totalElements", purchaseTableDtos.getTotalElements());
        metadata.put("totalPages", purchaseTableDtos.getTotalPages());
        metadata.put("isFirst", purchaseTableDtos.isFirst());
        metadata.put("isLast", purchaseTableDtos.isLast());
        metadata.put("size", purchaseTableDtos.getSize());
        metadata.put("number", purchaseTableDtos.getNumber());
        metadata.put("numberOfElements", purchaseTableDtos.getNumberOfElements());
        metadata.put("sort", purchaseTableDtos.getSort());
        return ResponseEntity.ok(ResponseUtil.success("Purchased fetched", purchaseTableDtos.getContent(), metadata));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<Purchase>> updatePurchaseStatus(@PathVariable String id, @RequestParam String status) {
        Purchase updatedPurchase = purchaseService.updatePurchaseStatus(id, status);
        return ResponseEntity.ok(ResponseUtil.success("Purchase status updated", updatedPurchase, null));
    }

    @GetMapping("/get-purchase/{id}")
    public ResponseEntity<ApiResponse<UpdatePurchaseDto>> getPurchaseStatusById(@PathVariable String id) {
        UpdatePurchaseDto updatePurchaseDto = purchaseService.getPurchaseStatusById(id);
        return ResponseEntity.ok(ResponseUtil.success("Purchase status updated", updatePurchaseDto, null));

    }
}
