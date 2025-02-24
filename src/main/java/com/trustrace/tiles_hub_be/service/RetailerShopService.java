package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.RetailerShopDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetailerShopService {

    @Autowired
    private RetailerShopDao retailerShopDao;

    public RetailerShop createRetailerShop(RetailerShop retailerShop) {
        return retailerShopDao.save(retailerShop);
    }

    public RetailerShop getRetailerShopById(String id) {
        return retailerShopDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retailer shop not found with ID: " + id));
    }

    public List<RetailerShop> getAllRetailerShops() {
        return retailerShopDao.findAll();
    }

    public RetailerShop updateRetailerShop(String id, RetailerShop updatedRetailerShop) {
        RetailerShop existingShop = getRetailerShopById(id);
        existingShop.setShopName(updatedRetailerShop.getShopName());
        existingShop.setContactPersonName(updatedRetailerShop.getContactPersonName());
        existingShop.setEmail(updatedRetailerShop.getEmail());
        existingShop.setPhone(updatedRetailerShop.getPhone());
        existingShop.setAddress(updatedRetailerShop.getAddress());
        existingShop.setCreditNote(updatedRetailerShop.getCreditNote());
        return retailerShopDao.save(existingShop);
    }

    public void deleteRetailerShop(String id) {
        if (!retailerShopDao.existsById(id)) {
            throw new ResourceNotFoundException("Retailer shop not found with ID: " + id);
        }
        retailerShopDao.deleteById(id);
    }
}
