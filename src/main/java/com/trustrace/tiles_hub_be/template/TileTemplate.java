package com.trustrace.tiles_hub_be.template;

import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    // Find a tile by name
    public Tile update(Tile tile) {
        return mongoTemplate.save(tile);
    }

    // Delete a tile by id
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Tile.class);
    }
}
