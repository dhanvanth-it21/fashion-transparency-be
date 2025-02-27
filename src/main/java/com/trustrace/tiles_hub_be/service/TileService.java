package com.trustrace.tiles_hub_be.service;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.tiles_hub_be.builder.TileDto;
import com.trustrace.tiles_hub_be.dao.TileDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.collections.tile.TileCategory;
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

    public void getLastCreatedTile() {

    }

    public String generateSku(TileDto tileDto) {
        String brandCode = tileDto.getBrandName().split(" ")[0].toUpperCase();
        String sizeCode = tileDto.getTileSize().getSize();
        String categoryCode = (tileDto.getCategory() == TileCategory.WALL) ? "W" : "F";
        String subCategoryCode = tileDto.getSubCategory().name().substring(0,1).toUpperCase();
        String finishingCode = tileDto.getFinishing().name().substring(0,1).toUpperCase();
        String serialNumber = String.format("%05d",1);
        String skuCode =  brandCode+"-"+sizeCode+"-"+categoryCode+"-"+subCategoryCode+"-"+finishingCode+"-"+serialNumber;
        return skuCode;
    }
}
