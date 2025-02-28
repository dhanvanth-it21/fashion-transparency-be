package com.trustrace.tiles_hub_be.template;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    public List<Tile> findAll() {
        return mongoTemplate.findAll(Tile.class);
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
}
