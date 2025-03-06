package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.damage.DamageReport;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageStatus;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public List<DamageReport> findByStatus(DamageStatus underReview) {
        return mongoTemplate.find(new Query(Criteria.where("status").is(underReview)), DamageReport.class);
    }

    public Page<DamageReport> getAllDamageReports(int page, int size, String sortBy, String sortDirection, String search, DamageStatus filterBy) {
        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();
        if(search == null || search.equals("")) {
            //nothing to do
        }
        else {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("damageLocation").regex(search, "i"),
                    Criteria.where("status").regex(search, "i")
            ));
        }

        if(filterBy != null) {
            query.addCriteria(Criteria.where("status").is(filterBy));
        }

        long total = mongoTemplate.count(query, DamageReport.class);

        query.with(pageable);

        List<DamageReport> damageReports = mongoTemplate.find(query, DamageReport.class);
        return new PageImpl<>(damageReports, pageable, total);
    }

    public List<DamageReport> findByOrderId(String orderId) {
        return mongoTemplate.find(new Query(Criteria.where("orderId").is(orderId)), DamageReport.class);
    }

    public Optional<DamageReport> findByTileIdAndOrderId(String tileId, String orderId) {
        Query query = new Query(Criteria.where("tileId").is(tileId).and("orderId").is(orderId));
        return Optional.ofNullable(mongoTemplate.findOne(query, DamageReport.class));
    }

}
