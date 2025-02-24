package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.PurchaseDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    public Purchase createPurchase(Purchase purchase) {
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
}
