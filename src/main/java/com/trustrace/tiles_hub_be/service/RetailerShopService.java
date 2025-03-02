package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.retailshop.RetailShopDto;
import com.trustrace.tiles_hub_be.builder.retailshop.RetailShopTableDto;
import com.trustrace.tiles_hub_be.dao.RetailerShopDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetailerShopService {

    @Autowired
    private RetailerShopDao retailerShopDao;

    public RetailerShop createRetailerShop(RetailerShop retailerShop) {
        return retailerShopDao.save(retailerShop);
    }

    public RetailShopDto getRetailerShopById(String id) {
        RetailerShop retailerShop =  retailerShopDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retailer shop not found with ID: " + id));
        RetailShopDto retailShopDto = RetailShopDto.builder()
                ._id(retailerShop.get_id())
                .shopName(retailerShop.getShopName())
                .contactPersonName(retailerShop.getContactPersonName())
                .email(retailerShop.getEmail())
                .phone(retailerShop.getPhone())
                .address(retailerShop.getAddress())
                .creditNote(retailerShop.getCreditNote())
                .build();
        return retailShopDto;
    }

//    public List<RetailerShop> getAllRetailerShops() {
//        return retailerShopDao.findAll();
//    }

    public RetailerShop updateRetailerShop(String id, RetailerShop updatedRetailerShop) {
        RetailerShop existingShop = retailerShopDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retailer shop not found with ID: " + id));
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

    public Page<RetailShopTableDto> getAllRetailerShopsTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Page<RetailerShop> paginated = retailerShopDao.getAllRetailShops(page, size, sortBy, sortDirection, search);
        List<RetailerShop> retailerShops = paginated.getContent();
       List<RetailShopTableDto> retailShopTableDtos = retailerShops.stream()
    .map(retailerShop -> RetailShopTableDto.builder()
            ._id(retailerShop.get_id())
            .shopName(retailerShop.getShopName())
            .phone(retailerShop.getPhone())
            .build())
    .toList();

        return new PageImpl<>(retailShopTableDtos, paginated.getPageable(), paginated.getTotalElements());
    }
}
