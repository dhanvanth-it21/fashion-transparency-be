package com.trustrace.fashion_transparency_be.template;

import com.trustrace.fashion_transparency_be.model.collections.damage.DamageLocation;
import com.trustrace.fashion_transparency_be.model.collections.damage.DamageReport;
import com.trustrace.fashion_transparency_be.model.collections.damage.DamageStatus;
import com.trustrace.fashion_transparency_be.model.user.Role;
import com.trustrace.fashion_transparency_be.model.user.UserEntity;
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

    public Page<DamageReport> getAllDamageReports(int page, int size, String sortBy, String sortDirection, String search, DamageStatus filterBy, String email) {
        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();

        System.out.println("Employee");
        if (getRoleByEmail(email)) {
            System.out.println(email);
            query.addCriteria(Criteria.where("reportedBy").is(email));
        }

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
        System.out.println(damageReports);
        return new PageImpl<>(damageReports, pageable, total);
    }

    public List<DamageReport> findByOrderId(String orderId) {
        return mongoTemplate.find(new Query(Criteria.where("orderId").is(orderId)), DamageReport.class);
    }

    public Optional<DamageReport> findByTileIdAndOrderId(String tileId, String orderId) {
        Query query = new Query(Criteria.where("tileId").is(tileId).and("orderId").is(orderId));
        return Optional.ofNullable(mongoTemplate.findOne(query, DamageReport.class));
    }

    public int getTotalUnderReviewDamageReports() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(DamageStatus.UNDER_REVIEW));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }

    public int getTotalUnderReviewDamageReportsForWarehouse() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(DamageStatus.UNDER_REVIEW).and("damageLocation").is(DamageLocation.AT_WAREHOUSE));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }

    public int getTotalUnderReviewDamageReportsForRetailShop() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(DamageStatus.UNDER_REVIEW).and("damageLocation").is(DamageLocation.TO_RETAIL_SHOP));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }

    public int getTotalUnderReviewDamageReportsForManufacturer() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(DamageStatus.UNDER_REVIEW).and("damageLocation").is(DamageLocation.FROM_MANUFACTURER));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }

    //helper
    public boolean getRoleByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        UserEntity userEntity = mongoTemplate.findOne(query, UserEntity.class);
        if (userEntity.getRoles().size() == 1) {
            for (Role role : userEntity.getRoles()) {
                if ("ROLE_EMPLOYEE".equals(role.getName())) {
                    return true;
                }
            }
        }
        return false;

    }

    public int getTotalDamageReports(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportedBy").is(email));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }

    public int getTotalApprovedDamageReports(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(DamageStatus.APPROVED).and("reportedBy").is(email));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }

    public int getTotalRejectedDamageReports(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(DamageStatus.REJECTED).and("reportedBy").is(email));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }

    public int getTotalUnderReviewDamageReports(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(DamageStatus.UNDER_REVIEW).and("reportedBy").is(email));
        return (int) mongoTemplate.count(query, DamageReport.class);
    }
}
