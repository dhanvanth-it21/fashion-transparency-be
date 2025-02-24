package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.template.TileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteTile(String id) {
        tileTemplate.deleteById(id);
    }

}
