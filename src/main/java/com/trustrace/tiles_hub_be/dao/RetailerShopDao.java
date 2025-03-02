package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import com.trustrace.tiles_hub_be.template.RetailerShopTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetailerShopDao {

    @Autowired
    private RetailerShopTemplate retailerShopTemplate;

    public RetailerShop save(RetailerShop retailerShop) {
        return retailerShopTemplate.save(retailerShop);
    }

    public Optional<RetailerShop> findById(String id) {
        return retailerShopTemplate.findById(id);
    }

//    public List<RetailerShop> findAll() {
////        return retailerShopTemplate.findAll();
//    }

    public boolean existsById(String id) {
        return retailerShopTemplate.existsById(id);
    }

    public void deleteById(String id) {
        retailerShopTemplate.deleteById(id);
    }

    public Page<RetailerShop> getAllRetailShops(int page, int size, String sortBy, String sortDirection, String search) {
        return retailerShopTemplate.findAll(page, size, sortBy, sortDirection, search);
    }

    public List<RetailerShop> searchRetailerShops(String search) {
        return retailerShopTemplate.searchRetailerShops(search);
    }
}
