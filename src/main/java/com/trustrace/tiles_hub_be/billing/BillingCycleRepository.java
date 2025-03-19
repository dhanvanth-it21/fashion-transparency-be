package com.trustrace.tiles_hub_be.billing;

import com.trustrace.tiles_hub_be.model.collections.Actor.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BillingCycleRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<BillingCycle> findByPaymentId(String paymentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("paymentLinkId").is(paymentId));
        return Optional.ofNullable(mongoTemplate.findOne(query, BillingCycle.class));
    }

    public BillingCycle save(BillingCycle billingCycle) {
        return mongoTemplate.save(billingCycle);
    }

    public Optional<BillingCycle> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, BillingCycle.class));
    }


    public Optional<BillingCycle> findActiveBillingCycle() {
        Query query = new Query();
        query.addCriteria(Criteria.where("active").is(true)
                .andOperator(Criteria.where("billingCycleStart").lte(new Date()),
                        Criteria.where("billingCycleEnd").gte(new Date())));

        return Optional.ofNullable(mongoTemplate.findOne(query, BillingCycle.class));
    }

    public Page<BillingCycle> getAllBillingCycleTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();
        if(search == null || search.equals("")) {
            //nothing to do
        } else {
            query.addCriteria(new Criteria().orOperator(

            ));
        }

        long total = mongoTemplate.count(query, BillingCycle.class);

        query.with(pageable);

        List<BillingCycle> billingCycles = mongoTemplate.find(query, BillingCycle.class);
        return new PageImpl<>(billingCycles, pageable, total);
    }
}
