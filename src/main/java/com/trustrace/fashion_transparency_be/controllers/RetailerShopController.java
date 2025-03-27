package com.trustrace.fashion_transparency_be.controllers;

import com.trustrace.fashion_transparency_be.builder.retailshop.RetailShopDto;
import com.trustrace.fashion_transparency_be.builder.retailshop.RetailShopNameDto;
import com.trustrace.fashion_transparency_be.builder.retailshop.RetailShopTableDto;
import com.trustrace.fashion_transparency_be.model.collections.Actor.RetailerShop;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ApiResponse;
import com.trustrace.fashion_transparency_be.model.responseWrapper.ResponseUtil;
import com.trustrace.fashion_transparency_be.service.RetailerShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
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
    public ResponseEntity<ApiResponse<RetailShopDto>> getRetailerShopById(@PathVariable String id) {
        RetailShopDto retailShopDto = retailerShopService.getRetailerShopById(id);
        return ResponseEntity.ok(ResponseUtil.success("Retailer shop found", retailShopDto, null));
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<RetailerShop>>> getAllRetailerShops() {
//        List<RetailerShop> shops = retailerShopService.getAllRetailerShops();
//        return ResponseEntity.ok(ResponseUtil.success("All retailer shops retrieved", shops, null));
//    }

    @GetMapping("search")
    public ResponseEntity<ApiResponse<List<RetailShopNameDto>>> searchRetailerShops(
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        List<RetailShopNameDto> retailShopNameDto = retailerShopService.searchRetailerShops(search);
        return ResponseEntity.ok(ResponseUtil.success("Retailer shops fetched", retailShopNameDto, null));
    }

    @GetMapping("table-details")
    public ResponseEntity<ApiResponse<List<RetailShopTableDto>>> getAllRetailerShopsTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        Page<RetailShopTableDto> retailerShopTableDtos = retailerShopService.getAllRetailerShopsTableDetails(page, size, sortBy, sortDirection, search);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("pageable", retailerShopTableDtos.getPageable());
        metadata.put("totalElements", retailerShopTableDtos.getTotalElements());
        metadata.put("totalPages", retailerShopTableDtos.getTotalPages());
        metadata.put("isFirst", retailerShopTableDtos.isFirst());
        metadata.put("isLast", retailerShopTableDtos.isLast());
        metadata.put("size", retailerShopTableDtos.getSize());
        metadata.put("number", retailerShopTableDtos.getNumber());
        metadata.put("numberOfElements", retailerShopTableDtos.getNumberOfElements());
        metadata.put("sort", retailerShopTableDtos.getSort());
        return ResponseEntity.ok(ResponseUtil.success("Retailer shops fetched", retailerShopTableDtos.getContent(), metadata));
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
