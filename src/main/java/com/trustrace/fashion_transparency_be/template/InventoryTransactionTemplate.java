package com.trustrace.fashion_transparency_be.template;

import com.trustrace.fashion_transparency_be.model.collections.activityLogs.InventoryTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryTransactionTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public InventoryTransaction save(InventoryTransaction transaction) {
        return mongoTemplate.save(transaction);
    }

    public Optional<InventoryTransaction> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, InventoryTransaction.class));
    }

    public List<InventoryTransaction> findAll() {
        return mongoTemplate.findAll(InventoryTransaction.class);
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), InventoryTransaction.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), InventoryTransaction.class);
    }
}
