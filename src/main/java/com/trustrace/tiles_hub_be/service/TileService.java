package com.trustrace.tiles_hub_be.service;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.tiles_hub_be.dao.TileDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
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
        if(checkTileIsExists(tile.get_id())) {
            return tileDao.updateTile(tile);
        }
        throw new ResourceNotFoundException("Tile with ID " + tile.get_id() + " not found");
    }

    public boolean checkTileIsExists(String id) {
        return tileDao.checkTileIsExists(id);
    }

    public void deleteTile(String id) {
        DeleteResult deleteResult = tileDao.deleteTile(id);
        if(!(deleteResult.getDeletedCount() > 0)) {
            throw new ResourceNotFoundException("Tile with ID " + id + " not found");
        }
    }
}
