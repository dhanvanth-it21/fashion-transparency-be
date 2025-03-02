package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.Actor.RetailerShop;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RetailerShopTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    public RetailerShop save(RetailerShop retailerShop) {
        return mongoTemplate.save(retailerShop);
    }

    public Optional<RetailerShop> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, RetailerShop.class));
    }

    public Page<RetailerShop> findAll(int page, int size, String sortBy, String sortDirection, String search) {
        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();
        if(search == null || search == "") {
            //nothing to do
        } else {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("shopName").regex(search, "i"),
                    Criteria.where("email").regex(search, "i"),
                    Criteria.where("phone").regex(search, "i")
            ));
        }

        long total = mongoTemplate.count(query, RetailerShop.class);

        query.with(pageable);

         List<RetailerShop> retailerShops = mongoTemplate.find(query, RetailerShop.class);
        return new PageImpl<>(retailerShops, pageable, total);
    }

    public boolean existsById(String id) {
        return mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), RetailerShop.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), RetailerShop.class);
    }

    public List<RetailerShop> searchRetailerShops(String search) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("shopName").regex(search, "i"),
                Criteria.where("email").regex(search, "i"),
                Criteria.where("phone").regex(search, "i")
        ));
        return mongoTemplate.find(query, RetailerShop.class);
    }
}
