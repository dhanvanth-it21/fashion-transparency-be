package com.trustrace.fashion_transparency_be.dao;

import com.trustrace.fashion_transparency_be.model.collections.Actor.Supplier;
import com.trustrace.fashion_transparency_be.template.SupplierTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierDao {

    @Autowired
    private SupplierTemplate supplierTemplate;

    public Supplier save(Supplier supplier) {
        return supplierTemplate.save(supplier);
    }

    public Optional<Supplier> findById(String id) {
        return supplierTemplate.findById(id);
    }

//    public List<Supplier> findAll() {
//        return supplierTemplate.findAll();
//    }

    public boolean existsById(String id) {
        return supplierTemplate.existsById(id);
    }

    public void deleteById(String id) {
        supplierTemplate.deleteById(id);
    }

    public Page<Supplier> getAllSuppliers(int page, int size, String sortBy, String sortDirection, String search) {
        return supplierTemplate.findAll(page, size, sortBy, sortDirection, search);
    }

    public List<Supplier> searchSuppliers(String search) {
        return supplierTemplate.searchSuppliers(search);
    }
}
