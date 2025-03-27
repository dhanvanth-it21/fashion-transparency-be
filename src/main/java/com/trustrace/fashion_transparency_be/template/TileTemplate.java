package com.trustrace.fashion_transparency_be.template;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.fashion_transparency_be.model.collections.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class TileTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Save a tile
    public Tile save(Tile tile) {
        return mongoTemplate.save(tile);
    }

    // Find a tile by id
    public Tile findById(String id) {
        return mongoTemplate.findById(id, Tile.class);
    }

    // Find all tiles
    public Page<Tile> findAll(int page, int size, String sortBy, String sortDirection, String search, String filterBy){

        Sort.Direction direction = sortDirection.toUpperCase().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Query query = new Query();
        query.addCriteria(Criteria.where("archived").is(false));

        if(filterBy.equalsIgnoreCase("LOW")) {
            AggregationExpression expr = ComparisonOperators.Lte.valueOf("qty").lessThanEqualTo("minimumStockLevel");
            query.addCriteria(Criteria.where("$expr").is(expr));


        }

        if(search == null || search.equals("")) {
            //nothing to do
        } else {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("skuCode").regex(search, "i"),
                    Criteria.where("tileSize").regex(search, "i"),
                    Criteria.where("brandName").regex(search, "i"),
                    Criteria.where("modelName").regex(search, "i"),
                    Criteria.where("color").regex(search, "i"),
                    Criteria.where("category").regex(search, "i"),
                    Criteria.where("subCategory").regex(search, "i"),
                    Criteria.where("finishing").regex(search, "i")
            ));
        }
        long total = mongoTemplate.count(query, Tile.class);

        query.with(pageable);
        List<Tile> tiles = mongoTemplate.find(query, Tile.class);


        return new PageImpl<>(tiles, pageable, total);
    }


    // Update the tile
    public Tile update(Tile tile) {
        return mongoTemplate.save(tile); //updates the already existing tile with the same id
    }

    //tile existence
    public boolean isExists(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.exists(query,Tile.class);
    }

    // Delete a tile by id
    public DeleteResult deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult deleteResult = mongoTemplate.remove(query, Tile.class);
        return deleteResult;
    }


    public Optional<Tile> findLastCreatedTile() {
        Query query = new Query();
        query.limit(1);
        query.with(Sort.by(Sort.Order.desc("createdAt")));
        return Optional.ofNullable(mongoTemplate.findOne(query, Tile.class));
    }

    public List<Tile> searchTiles(String search, String brandName) {
        Query query = new Query();
        if (!brandName.isEmpty()) {
            try {
                int brandId = Integer.parseInt(brandName);
                if (brandId != 0) {
                    System.out.println(brandName + " " + search);
                    System.out.println(brandId == 0);
                    query.addCriteria(Criteria.where("brandName").regex(brandName, "i"));
                }
            } catch (NumberFormatException e) {
                query.addCriteria(Criteria.where("brandName").regex(brandName, "i"));
            }
        }
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("skuCode").regex(search, "i"),
                Criteria.where("tileSize").regex(search, "i"),
                Criteria.where("brandName").regex(search, "i"),
                Criteria.where("modelName").regex(search, "i"),
                Criteria.where("color").regex(search, "i"),
                Criteria.where("category").regex(search, "i"),
                Criteria.where("subCategory").regex(search, "i"),
                Criteria.where("finishing").regex(search, "i")
        ));
        query.limit(10);
        List<Tile> tiles =  mongoTemplate.find(query, Tile.class);
        System.out.println(tiles);
        return tiles;
    }



    public Tile findBySkuCode(String tileSku) {
        Query query = new Query();
        query.addCriteria(Criteria.where("skuCode").is(tileSku));
        return mongoTemplate.findOne(query, Tile.class);
    }

    public int getTotalTileCount() {
        Query query = new Query();
        query.addCriteria(Criteria.where("archived").is(false));
        return (int)mongoTemplate.count(query, Tile.class);
    }

    public Integer getTotalLowStocks() {
        Query query = new Query();
        AggregationExpression expr = ComparisonOperators.Lte.valueOf("qty").lessThanEqualTo("minimumStockLevel");
        query.addCriteria(Criteria.where("$expr").is(expr));
        return (int)mongoTemplate.count(query, Tile.class);
    }
}
