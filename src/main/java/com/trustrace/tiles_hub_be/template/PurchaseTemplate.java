package com.trustrace.tiles_hub_be.template;


import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Order;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.Purchase;
import com.trustrace.tiles_hub_be.model.collections.tiles_list.PurchaseItem;
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

    public Page<Purchase> getAllPurchases(int page, int size, String sortBy, String sortDirection, String search) {
        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();
        if(search == null || search == "") {
            //nothing to do
        } else {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("brandName").regex(search, "i"),
                    Criteria.where("email").regex(search, "i")
            ));
        }

        long total = mongoTemplate.count(query, Purchase.class);

        query.with(pageable);

        List<Purchase> purchases = mongoTemplate.find(query, Purchase.class);
        return new PageImpl<>(purchases, pageable, total);
    }

    public void updateStockByPurchaseItems(List<PurchaseItem> itemList) {
        itemList.forEach(purchaseItem -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(purchaseItem.getTileId()));
            Tile tile = mongoTemplate.findOne(query, Tile.class);
            if(tile != null) {
                tile.setQty(tile.getQty() + purchaseItem.getAddQty());
                mongoTemplate.save(tile);
            }
        });
    }
}
