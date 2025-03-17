package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.orders.OrderTableDto;
import com.trustrace.tiles_hub_be.builder.purchases.*;
import com.trustrace.tiles_hub_be.dao.PurchaseDao;
import com.trustrace.tiles_hub_be.dao.TileDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseItem;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    TileService tileService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    TileDao tileDao;

    public Purchase createPurchase(NewPurchaseDto newPurchaseDto) {
        String purchaseId = generatePurchaseId();
        Purchase purchase = Purchase.builder()
                .purchaseId(purchaseId)
                .purchaseId(newPurchaseDto.getPurchaseId())
                .supplierId(newPurchaseDto.getSupplierId())
                .recordedBy(getAuthenticatedUserEmail())
                .itemList(newPurchaseDto.getItemList())
                .damagePercentage(newPurchaseDto.getDamagePercentage())
                .status(PurchaseStatus.PENDING)
                .build();

        return purchaseDao.save(purchase);

    }

    public PurchaseDetail getPurchaseDetailById(String id) {
        Purchase purchase = purchaseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + id));
        return PurchaseDetail.builder()
                .purchaseId(purchase.getPurchaseId())
                .recordedBy(purchase.getRecordedBy())
                .approvedBy(getApprovedBy(purchase.getApprovedBy()))
                .brandName(supplierService.getSupplierById(purchase.getSupplierId()).getBrandName())
                .createdAt(purchase.getCreatedAt())
                .damagePercentage(purchase.getDamagePercentage())
                .status(purchase.getStatus())
                .itemList(provideItemListDetails(purchase.getItemList()))
                .build();

    }

    private String getApprovedBy(String approvedBy) {
        if(approvedBy == null) {
            return "Not yet Approved";
        }
        else {
            return approvedBy;
        }
    }

    private List<ItemListDetails> provideItemListDetails(List<PurchaseItem> itemLists) {
        return itemLists.stream().map(
                itemList ->
                        ItemListDetails.builder()
                                .qty(itemList.getAddQty())
                                .skuCode(tileDao.findById(itemList.getTileId()).getSkuCode())
                                .build()
        ).collect(Collectors.toList());
    }


    public void deletePurchase(String id) {
        if (!purchaseDao.existsById(id)) {
            throw new ResourceNotFoundException("Purchase not found with ID: " + id);
        }
        purchaseDao.deleteById(id);
    }

    public Page<PurchaseTableDto> getAllPurchasesTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        String email = getAuthenticatedUserEmail();
        Page<Purchase> paginated = purchaseDao.getAllPurchases(page, size, sortBy, sortDirection, search, email);
        List<Purchase> purchases = paginated.getContent();
        List<PurchaseTableDto> purchaseTableDtos = purchases.stream()
                .map(purchase -> PurchaseTableDto.builder()
                        ._id(purchase.get_id())
                        .purchaseId(purchase.getPurchaseId())
                        .createdAt(purchase.getCreatedAt())
                        .recordedBy(getAuthenticatedUserEmail())
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
            purchase.setApprovedBy(getAuthenticatedUserEmail());
            if (PurchaseStatus.valueOf(status) == PurchaseStatus.VERIFIED) {
                tileService.updateStockByPurchaseItems(purchase.getItemList());
            }
            return purchaseDao.save(purchase);
        }

        throw new ResourceNotFoundException("Invalid status");
    }

    private Purchase getByPurchaseId(String id) {
        System.out.println(id);
        return purchaseDao.findByPurchaseId(id).orElseThrow(
                () -> new ResourceNotFoundException(" No Purchase found with purchase id")
        );
    }

    private Purchase getPurchaseById(String id) {
        System.out.println(id);
        return purchaseDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(" No Purchase found with purchase id")
        );
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

    private String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }
}
