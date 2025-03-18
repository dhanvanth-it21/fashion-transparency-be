package com.trustrace.tiles_hub_be.billing;

import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/billing-cycle")
public class BillingCycleController {

    @Autowired
    private BillingCycleService billingCycleService;

    @GetMapping("table-details")
    public ResponseEntity<ApiResponse<List<BillingCycleDto>>> getAllBillingCycleTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        Page<BillingCycleDto> billingCycleDtos = billingCycleService.getAllBillingCycleTableDetails(page, size, sortBy, sortDirection, search);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("pageable", billingCycleDtos.getPageable());
        metadata.put("totalElements", billingCycleDtos.getTotalElements());
        metadata.put("totalPages", billingCycleDtos.getTotalPages());
        metadata.put("isFirst", billingCycleDtos.isFirst());
        metadata.put("isLast", billingCycleDtos.isLast());
        metadata.put("size", billingCycleDtos.getSize());
        metadata.put("number", billingCycleDtos.getNumber());
        metadata.put("numberOfElements", billingCycleDtos.getNumberOfElements());
        metadata.put("sort", billingCycleDtos.getSort());
        return ResponseEntity.ok(ResponseUtil.success("Billing Cycle fetched", billingCycleDtos.getContent(), metadata));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BillingCycleDetailDto>> getBillingCycleById(@PathVariable String id) {
        BillingCycleDetailDto billingCycleDetailDto = billingCycleService.getBillingCycleDetailById(id);
        return ResponseEntity.ok(ResponseUtil.success("Billing Cycle found", billingCycleDetailDto, null));
    }
}
