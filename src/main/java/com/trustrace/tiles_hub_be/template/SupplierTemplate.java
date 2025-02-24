package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.Actor.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Supplier> findAll() {
        return mongoTemplate.findAll(Supplier.class);
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), Supplier.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Supplier.class);
    }
}
