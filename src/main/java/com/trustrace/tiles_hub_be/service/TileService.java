package com.trustrace.tiles_hub_be.service;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.tiles_hub_be.builder.TileDto;
import com.trustrace.tiles_hub_be.builder.TileTableDto;
import com.trustrace.tiles_hub_be.dao.TileDao;
import com.trustrace.tiles_hub_be.exceptionHandlers.ResourceNotFoundException;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.collections.tile.TileCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
//        return tileDao.getAllTiles();
        return null;
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

    public int getLastCreatedTileSerialCode() {
        String[] parts = new String[0];
             Optional<Tile> tile = tileDao.getLastCreatedTile();
             if(tile.isPresent()) {
                 parts = tile.get().getSkuCode().split("-");
             }
        return Integer.parseInt(parts[parts.length - 1]);
    }

    public String generateSku(TileDto tileDto) {
        String brandCode = tileDto.getBrandName().split(" ")[0].toUpperCase();
        String sizeCode = tileDto.getTileSize().getSize();
        String categoryCode = (tileDto.getCategory() == TileCategory.WALL) ? "W" : "F";
        String subCategoryCode = tileDto.getSubCategory().name().substring(0,1).toUpperCase();
        String finishingCode = tileDto.getFinishing().name().substring(0,1).toUpperCase();
        int lastSerialCode = getLastCreatedTileSerialCode();
        String serialNumber = String.format("%05d",lastSerialCode + 11);
        String skuCode =  brandCode+"-"+sizeCode+"-"+categoryCode+"-"+subCategoryCode+"-"+finishingCode+"-"+serialNumber;
        return skuCode;
    }

    public Page<TileTableDto> getAllTilesTableDetails(int page, int size, String sortBy, String sortDirection) {
        Page<Tile> paginated = tileDao.getAllTiles(page, size, sortBy, sortDirection);
        List<Tile> tiles = paginated.getContent();
        List<TileTableDto> tileTableDtos = tiles.stream()
                .map(tile -> {
                    TileTableDto tileTableDto = new TileTableDto();
                    tileTableDto.set_id(tile.get_id());
                    tileTableDto.setSkuCode(tile.getSkuCode());
                    tileTableDto.setTileSize(tile.getTileSize());
                    tileTableDto.setBrandName(tile.getBrandName());
                    tileTableDto.setModelName(tile.getModelName());
                    tileTableDto.setQty(tile.getQty());
                    tileTableDto.setPiecesPerBox(tile.getPiecesPerBox());
                    return tileTableDto;
                })
                .toList();
        return new PageImpl<>(tileTableDtos, paginated.getPageable(), paginated.getTotalElements());

    }
}
