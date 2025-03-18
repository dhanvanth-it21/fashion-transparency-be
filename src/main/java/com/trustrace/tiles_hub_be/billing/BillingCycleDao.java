package com.trustrace.tiles_hub_be.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillingCycleDao {

    @Autowired
    private BillingCycleRepository billingCycleRepository;

    public BillingCycle save(BillingCycle billingCycle) {
        return billingCycleRepository.save(billingCycle);
    }

    public Optional<BillingCycle> findById(String id) {
        return billingCycleRepository.findById(id);
    }

    public Optional<BillingCycle> findActiveBillingCycle() {
        return billingCycleRepository.findActiveBillingCycle();
    }

    public Page<BillingCycle> getAllBillingCycleTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        return billingCycleRepository.getAllBillingCycleTableDetails(page, size, sortBy, sortDirection, search);
    }
}
