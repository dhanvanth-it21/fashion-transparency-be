package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.builder.retailshop.RetailShopTableDto;
import com.trustrace.tiles_hub_be.builder.suppier.SupplierTableDto;
import com.trustrace.tiles_hub_be.model.collections.Actor.Supplier;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<ApiResponse<Supplier>> createSupplier(@RequestBody Supplier supplier) {
        Supplier createdSupplier = supplierService.createSupplier(supplier);
        return ResponseEntity.ok(ResponseUtil.success("Supplier created successfully", createdSupplier, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Supplier>> getSupplierById(@PathVariable String id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(ResponseUtil.success("Supplier found", supplier, null));
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<Supplier>>> getAllSuppliers() {
//        List<Supplier> suppliers = supplierService.getAllSuppliers();
//        return ResponseEntity.ok(ResponseUtil.success("All suppliers retrieved", suppliers, null));
//    }

    @GetMapping("table-details")
    public ResponseEntity<ApiResponse<List<SupplierTableDto>>> getAllSupplierTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        Page<SupplierTableDto> supplierTableDtos = supplierService.getAllSuppliersTableDetails(page, size, sortBy, sortDirection, search);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("pageable", supplierTableDtos.getPageable());
        metadata.put("totalElements", supplierTableDtos.getTotalElements());
        metadata.put("totalPages", supplierTableDtos.getTotalPages());
        metadata.put("isFirst", supplierTableDtos.isFirst());
        metadata.put("isLast", supplierTableDtos.isLast());
        metadata.put("size", supplierTableDtos.getSize());
        metadata.put("number", supplierTableDtos.getNumber());
        metadata.put("numberOfElements", supplierTableDtos.getNumberOfElements());
        metadata.put("sort", supplierTableDtos.getSort());
        return ResponseEntity.ok(ResponseUtil.success("Suppliers fetched", supplierTableDtos.getContent(), metadata));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Supplier>> updateSupplier(@PathVariable String id, @RequestBody Supplier supplier) {
        Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok(ResponseUtil.success("Supplier updated successfully", updatedSupplier, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSupplier(@PathVariable String id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(ResponseUtil.success("Supplier deleted successfully", null, null));
    }
}
