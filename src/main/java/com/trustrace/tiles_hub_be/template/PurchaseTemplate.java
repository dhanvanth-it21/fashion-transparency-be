package com.trustrace.tiles_hub_be.template;


import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseItem;
import com.trustrace.tiles_hub_be.model.user.Role;
import com.trustrace.tiles_hub_be.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Purchase save(Purchase purchase) {
        return mongoTemplate.save(purchase);
    }

    public Optional<Purchase> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Purchase.class));
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), Purchase.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Purchase.class);
    }

    public Page<Purchase> getAllPurchases(int page, int size, String sortBy, String sortDirection, String search, String email) {
        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();
        if (getRoleByEmail(email)) {
            query.addCriteria(Criteria.where("recordedBy").is(email));
        }

        if(search == null || search.equals("")) {
            //nothing to do
        } else {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("status").regex(search, "i"),
                    Criteria.where("purchaseId").regex(search, "i")
            ));
        }

        long total = mongoTemplate.count(query, Purchase.class);
        query.with(pageable);


        List<Purchase> purchases = mongoTemplate.find(query, Purchase.class);
        return new PageImpl<>(purchases, pageable, total);
    }


    public List<Purchase> searchPurchases(String search) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("purchaseId").regex(search, "i"),
                Criteria.where("brandName").regex(search, "i"),
                Criteria.where("email").regex(search, "i")
        ));
        return mongoTemplate.find(query, Purchase.class);
    }

    public Optional<Purchase> findByPurchaseId(String givenId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("purchaseId").is(givenId));
        return Optional.ofNullable(mongoTemplate.findOne(query, Purchase.class));
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
}
