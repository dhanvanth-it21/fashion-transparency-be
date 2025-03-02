package com.trustrace.tiles_hub_be.template;


import com.trustrace.tiles_hub_be.model.collections.Actor.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplierTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Supplier save(Supplier supplier) {
        return mongoTemplate.save(supplier);
    }

    public Optional<Supplier> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Supplier.class));
    }

    public Page<Supplier> findAll(int page, int size, String sortBy, String sortDirection, String search) {
        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();
        if(search == null || search == "") {
            //nothing to do
        } else {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("brandName").regex(search, "i"),
                    Criteria.where("email").regex(search, "i"),
                    Criteria.where("phone").regex(search, "i")
            ));
        }

        long total = mongoTemplate.count(query, Supplier.class);

        query.with(pageable);

        List<Supplier> suppliers = mongoTemplate.find(query, Supplier.class);
        return new PageImpl<>(suppliers, pageable, total);
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), Supplier.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Supplier.class);
    }
}
