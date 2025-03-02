package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.builder.retailshop.RetailShopTableDto;
import com.trustrace.tiles_hub_be.builder.suppier.SupplierTableDto;
import com.trustrace.tiles_hub_be.dao.SupplierDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import com.trustrace.tiles_hub_be.model.collections.Actor.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    public Supplier createSupplier(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    public Supplier getSupplierById(String id) {
        return supplierDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with ID: " + id));
    }

//    public List<Supplier> getAllSuppliers() {
//        return supplierDao.findAll();
//    }

    public Supplier updateSupplier(String id, Supplier updatedSupplier) {
        Supplier existingSupplier = getSupplierById(id);
        existingSupplier.setBrandName(updatedSupplier.getBrandName());
        existingSupplier.setContactPersonName(updatedSupplier.getContactPersonName());
        existingSupplier.setEmail(updatedSupplier.getEmail());
        existingSupplier.setPhone(updatedSupplier.getPhone());
        existingSupplier.setAddress(updatedSupplier.getAddress());
        return supplierDao.save(existingSupplier);
    }

    public void deleteSupplier(String id) {
        if (!supplierDao.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with ID: " + id);
        }
        supplierDao.deleteById(id);
    }

    public Page<SupplierTableDto> getAllSuppliersTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Page<Supplier> paginated = supplierDao.getAllSuppliers(page, size, sortBy, sortDirection, search);
        List<Supplier> suppliers = paginated.getContent();
        List<SupplierTableDto> supplierTableDtos = suppliers.stream()
                .map(supplier -> SupplierTableDto.builder()
                        ._id(supplier.get_id())
                        .brandName(supplier.getBrandName())
                        .phone(supplier.getPhone())
                        .build())
                .toList();

        return new PageImpl<>(supplierTableDtos, paginated.getPageable(), paginated.getTotalElements());
    }
}
