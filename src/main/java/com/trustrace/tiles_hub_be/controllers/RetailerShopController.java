package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.RetailerShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retailer-shop")
public class RetailerShopController {

    @Autowired
    private RetailerShopService retailerShopService;

    @PostMapping
    public ResponseEntity<ApiResponse<RetailerShop>> createRetailerShop(@RequestBody RetailerShop retailerShop) {
        RetailerShop createdShop = retailerShopService.createRetailerShop(retailerShop);
        return ResponseEntity.ok(ResponseUtil.success("Retailer shop created successfully", createdShop, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RetailerShop>> getRetailerShopById(@PathVariable String id) {
        RetailerShop retailerShop = retailerShopService.getRetailerShopById(id);
        return ResponseEntity.ok(ResponseUtil.success("Retailer shop found", retailerShop, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RetailerShop>>> getAllRetailerShops() {
        List<RetailerShop> shops = retailerShopService.getAllRetailerShops();
        return ResponseEntity.ok(ResponseUtil.success("All retailer shops retrieved", shops, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RetailerShop>> updateRetailerShop(@PathVariable String id, @RequestBody RetailerShop retailerShop) {
        RetailerShop updatedShop = retailerShopService.updateRetailerShop(id, retailerShop);
        return ResponseEntity.ok(ResponseUtil.success("Retailer shop updated successfully", updatedShop, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRetailerShop(@PathVariable String id) {
        retailerShopService.deleteRetailerShop(id);
        return ResponseEntity.ok(ResponseUtil.success("Retailer shop deleted successfully", null, null));
    }
}
