package com.trustrace.tiles_hub_be.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BillingCycleService {
    @Autowired
    private BillingCycleDao billingCycleDao;

    public BillingCycle createBillingCycle(BillingCycle billingCycle) {
        return billingCycleDao.save(billingCycle);
    }

    public Optional<BillingCycle> getBillingCycle(String id) {
        return billingCycleDao.findById(id);
    }




    public void logApiUsage(String method) {
        Optional<BillingCycle> currentBillingCycle = getCurrentBillingCycle();
        BillingCycle billingCycle;
        if (currentBillingCycle.isPresent()) {
            billingCycle = currentBillingCycle.get();
        } else {
            billingCycle = createNewBillingCycle(null);
        }

            if (hasBillingCycleEnded(billingCycle)) {
                billingCycle = createNewBillingCycle(billingCycle);
            }

        if (billingCycle.getApiUsage() == null) {
            billingCycle.setApiUsage(new HashMap<>());
        }


            ApiUsageDetails apiUsageDetails = billingCycle.getApiUsage().getOrDefault(method, new ApiUsageDetails(0,0));

            apiUsageDetails.setCount(apiUsageDetails.getCount() + 1);
            apiUsageDetails.setPricePerRequest(0.30);


            billingCycle.getApiUsage().put(method, apiUsageDetails);

            billingCycleDao.save(billingCycle);

    }

    private BillingCycle createNewBillingCycle(BillingCycle billingCycle) {
        if (billingCycle != null) {
            billingCycle.setActive(false);
            billingCycleDao.save(billingCycle);
        }


        BillingCycle newBillingCycle = BillingCycle.builder()
                .billingCycleStart(getStartOfMonth())
                .billingCycleEnd(getEndOfMonth())
                .apiUsage(new HashMap<>())
                .payment(false)
                .active(true)
                .build();

        return billingCycleDao.save(newBillingCycle);
    }

    public static Date getStartOfMonth() {
        return Date.from(LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date getEndOfMonth() {
        return Date.from(LocalDate.now()
                .plusMonths(1)
                .withDayOfMonth(1)
                .minusDays(1)
                .atTime(23, 59, 59)  // Ensures it includes full day
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }


    private boolean hasBillingCycleEnded(BillingCycle billingCycle) {
        return billingCycle.getBillingCycleEnd().before(new Date());
    }

    private Optional<BillingCycle> getCurrentBillingCycle() {
        return billingCycleDao.findActiveBillingCycle();
    }

    public Page<BillingCycleDto> getAllBillingCycleTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Page<BillingCycle> paginated = billingCycleDao.getAllBillingCycleTableDetails(page, size, sortBy, sortDirection, search);
        List<BillingCycle> billingCycles = paginated.getContent();
        List<BillingCycleDto> billingCycleDtos = billingCycles.stream()
                .map(billingCycle -> BillingCycleDto.builder()
                        ._id(billingCycle.get_id())
                        .billingCycleStart(billingCycle.getBillingCycleStart())
                        .billingCycleEnd(billingCycle.getBillingCycleEnd())
                        .payment(billingCycle.isPayment())
                        .totalAmount(getTotalAmount(billingCycle))
                        .build()
                ).toList();
        return new PageImpl<>(billingCycleDtos, paginated.getPageable(), paginated.getTotalElements());
    }

    private double getTotalAmount(BillingCycle billingCycle) {
        double totalAmount = 0;
        for (ApiUsageDetails apiUsageDetails : billingCycle.getApiUsage().values()) {
            totalAmount += Math.round(apiUsageDetails.getCount() * apiUsageDetails.getPricePerRequest() * 100.0) / 100.0;
        }
        return totalAmount;
    }

    public BillingCycleDetailDto getBillingCycleDetailById(String id) {
        BillingCycle billingCycle = getBillingCycle(id).orElseThrow(
                () -> new RuntimeException("No billing cycle found with idi ")
        );
        return BillingCycleDetailDto.builder()
                ._id(billingCycle.get_id())
                .usageList(getUageList(billingCycle))
                .build();
    }

    private List<RequestDetails> getUageList(BillingCycle billingCycle) {
        return billingCycle.getApiUsage().entrySet().stream()
                .map(entry -> RequestDetails.builder()
                        .method(entry.getKey())
                        .count(entry.getValue().getCount())
                        .pricePerRequest(entry.getValue().getPricePerRequest())
                        .totalAmount(Math.round(entry.getValue().getCount() * entry.getValue().getPricePerRequest() * 100.0) / 100.0)
                        .build()
                ).toList();
    }
}
