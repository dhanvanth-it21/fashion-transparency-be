package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.orders.OrderTableDto;
import com.trustrace.tiles_hub_be.builder.purchases.NewPurchaseDto;
import com.trustrace.tiles_hub_be.builder.purchases.PurchaseDamageDto;
import com.trustrace.tiles_hub_be.builder.purchases.PurchaseTableDto;
import com.trustrace.tiles_hub_be.builder.purchases.UpdatePurchaseDto;
import com.trustrace.tiles_hub_be.dao.PurchaseDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private SupplierService supplierService;

    @Autowired TileService tileService;

    public Purchase createPurchase(NewPurchaseDto newPurchaseDto) {
        String purchaseId = generatePurchaseId();
        Purchase purchase = Purchase.builder()
                .purchaseId(purchaseId)
                .purchaseId(newPurchaseDto.getPurchaseId())
                .supplierId(newPurchaseDto.getSupplierId())
                .recordedByUserId(newPurchaseDto.getRecordedByUserId())
                .itemList(newPurchaseDto.getItemList())
                .damagePercentage(newPurchaseDto.getDamagePercentage())
                .status(PurchaseStatus.PENDING)
                .build();

        return purchaseDao.save(purchase);

    }

    public Purchase getPurchaseById(String id) {
        return purchaseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + id));
    }

    public void deletePurchase(String id) {
        if (!purchaseDao.existsById(id)) {
            throw new ResourceNotFoundException("Purchase not found with ID: " + id);
        }
        purchaseDao.deleteById(id);
    }

    public Page<PurchaseTableDto> getAllPurchasesTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Page<Purchase> paginated = purchaseDao.getAllPurchases(page, size, sortBy, sortDirection, search);
        List<Purchase> purchases = paginated.getContent();
        List<PurchaseTableDto> purchaseTableDtos = purchases.stream()
                .map(purchase -> PurchaseTableDto.builder()
                        ._id(purchase.get_id())
                        .purchaseId(purchase.getPurchaseId())
                        .createdAt(purchase.getCreatedAt())
                        .recordedByUserId(purchase.getRecordedByUserId())
                        .brandName(supplierService.getSupplierById(purchase.getSupplierId()).getBrandName())
                        .status(purchase.getStatus())
                        .build())
                .toList();
        return new PageImpl<>(purchaseTableDtos, paginated.getPageable(), paginated.getTotalElements());
    }

    public Purchase updatePurchaseStatus(String id, String status) {
        if (PurchaseStatus.valueOf(status) == PurchaseStatus.VERIFIED
                || PurchaseStatus.valueOf(status) == PurchaseStatus.REJECTED
                || PurchaseStatus.valueOf(status) == PurchaseStatus.PENDING) {
            Purchase purchase = getPurchaseById(id);
            if (purchase.getStatus() == PurchaseStatus.VERIFIED) {
                throw new ResourceNotFoundException("Purchase already verified");
            }
            purchase.setStatus(PurchaseStatus.valueOf(status));
            if (PurchaseStatus.valueOf(status) == PurchaseStatus.VERIFIED) {
                tileService.updateStockByPurchaseItems(purchase.getItemList());
            }
            return purchaseDao.save(purchase);
        }

        throw new ResourceNotFoundException("Invalid status");
    }



    public UpdatePurchaseDto getPurchaseStatusById(String id) {
        Purchase purchase = getPurchaseById(id);
        return UpdatePurchaseDto.builder()
                ._id(purchase.get_id())
                .brandName(supplierService.getSupplierById(purchase.getSupplierId()).getBrandName())
                .status(purchase.getStatus())
                .build();
    }

    public String generatePurchaseId() {
       return "PUR" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
    }

    public List<PurchaseDamageDto> searchPurchases(String search) {
        List<Purchase> purchases = purchaseDao.searchPurchases(search);
        return purchases.stream()
                .map(purchase -> PurchaseDamageDto.builder()
                        ._id(purchase.get_id())
                        .purchaseId(purchase.getPurchaseId())
                        .brandName(supplierService.getSupplierById(purchase.getSupplierId()).getBrandName())
                        .build())
                .toList();
    }
}
