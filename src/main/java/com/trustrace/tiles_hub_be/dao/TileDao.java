package com.trustrace.tiles_hub_be.dao;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.tiles_hub_be.builder.tile.TileQtyDto;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.template.TileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public Page<Tile> getAllTiles(int page, int size, String sortBy, String sortDirection, String search) {
        return tileTemplate.findAll(page, size, sortBy, sortDirection, search);
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

    public List<Tile> searchTiles(String search) {
        return tileTemplate.searchTiles(search);
    }

    public void updateStockByOrderItem(String tileId, int requiredQty) {
        tileTemplate.updateStockByOrderItem(tileId, requiredQty);
    }

    public Tile findById(String tileId) {
        return tileTemplate.findById(tileId);
    }

    public void save(Tile tile) {
        tileTemplate.save(tile);
    }

    public Tile findBySkuCode(String tileSku) {
        return tileTemplate.findBySkuCode(tileSku);
    }
}
