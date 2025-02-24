package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.damage.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DamageReportTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public DamageReport save(DamageReport damageReport) {
        return mongoTemplate.save(damageReport);
    }

    public Optional<DamageReport> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, DamageReport.class));
    }

    public List<DamageReport> findAll() {
        return mongoTemplate.findAll(DamageReport.class);
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), DamageReport.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), DamageReport.class);
    }
}
