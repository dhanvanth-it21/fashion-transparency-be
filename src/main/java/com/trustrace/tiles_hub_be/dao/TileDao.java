package com.trustrace.tiles_hub_be.dao;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.template.TileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TileDao {

    @Autowired
    private TileTemplate tileTemplate;

    public Tile createTile(Tile tile) {
        return tileTemplate.save(tile);
    }

    public Tile getTileById(String id) {
        return tileTemplate.findById(id);
    }

    public List<Tile> getAllTiles() {
        return tileTemplate.findAll();
    }

    public Tile updateTile(Tile tile) {
        return tileTemplate.update(tile);
    }

    public boolean checkTileIsExists(String id) {
        return tileTemplate.isExists(id);
    }

    public DeleteResult deleteTile(String id) {
        return tileTemplate.deleteById(id);
    }

    public Optional<Tile> getLastCreatedTile() {
        return tileTemplate.findLastCreatedTile();
    }
}
