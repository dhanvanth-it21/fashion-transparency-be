package com.trustrace.tiles_hub_be.dao;


import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseItem;
import com.trustrace.tiles_hub_be.template.PurchaseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseDao {

    @Autowired
    private PurchaseTemplate purchaseTemplate;

    public Purchase save(Purchase purchase) {
        return purchaseTemplate.save(purchase);
    }

    public Optional<Purchase> findById(String id) {
        return purchaseTemplate.findById(id);
    }

    public boolean existsById(String id) {
        return purchaseTemplate.existsById(id);
    }

    public void deleteById(String id) {
        purchaseTemplate.deleteById(id);
    }


    public Page<Purchase> getAllPurchases(int page, int size, String sortBy, String sortDirection, String search) {
        return purchaseTemplate.getAllPurchases(page, size, sortBy, sortDirection, search);
    }


    public List<Purchase> searchPurchases(String search) {
        return purchaseTemplate.searchPurchases(search);
    }

    public Optional<Purchase> findByPurchaseId(String givenId) {
        return purchaseTemplate.findByPurchaseId(givenId);
    }
}
