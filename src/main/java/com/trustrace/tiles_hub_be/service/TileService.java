package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.TileDao;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TileService {

    @Autowired
    private TileDao tileDao;


    public Tile createTile(Tile tile) {
        return tileDao.createTile(tile);
    }

    public Tile getTileById(String id) {
        return tileDao.getTileById(id);
    }

    public List<Tile> getAllTiles() {
        return tileDao.getAllTiles();
    }

    public Tile updateTile(Tile tile) {
        return tileDao.updateTile(tile);
    }

    public void deleteTile(String id) {
        tileDao.deleteTile(id);
    }
}
