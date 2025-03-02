package com.trustrace.tiles_hub_be.service;

import com.mongodb.client.result.DeleteResult;
import com.trustrace.tiles_hub_be.builder.tile.TileDetailDto;
import com.trustrace.tiles_hub_be.builder.tile.TileDto;
import com.trustrace.tiles_hub_be.builder.tile.TileQtyDto;
import com.trustrace.tiles_hub_be.builder.tile.TileTableDto;
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
            TileDto tileDto = convertToTileDto(tile);
            String skuCode = generateSku(tileDto);
            String[] code = skuCode.split("-");
            code[code.length - 1] = tile.getSkuCode().split("-")[code.length-1];
            String newSkuCode = String.join("-", code);
            tile.setSkuCode(newSkuCode);
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

    public Page<TileTableDto> getAllTilesTableDetails(int page, int size, String sortBy, String sortDirection, String search) {
        Page<Tile> paginated = tileDao.getAllTiles(page, size, sortBy, sortDirection, search);
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

    public TileDetailDto getTileDetailById(String id) {
        Tile tile = getTileById(id);
        if(tile != null) {
            return TileDetailDto.builder()
                    .skuCode(tile.getSkuCode())
                    .tileSize(tile.getTileSize())
                    .brandName(tile.getBrandName())
                    .modelName(tile.getModelName())
                    .color(tile.getColor())
                    .qty(tile.getQty())
                    .piecesPerBox(tile.getPiecesPerBox())
                    .category(tile.getCategory())
                    .subCategory(tile.getSubCategory())
                    .finishing(tile.getFinishing())
                    .minimumStockLevel(tile.getMinimumStockLevel())
                    .createdAt(tile.getCreatedAt())
                    .updatedAt(tile.getUpdatedAt())
                    .build();
        }
        return null;
    }


    private TileDto convertToTileDto(Tile tile) {
        return TileDto.builder()
                .skuCode(tile.getSkuCode())
                .tileSize(tile.getTileSize())
                .brandName(tile.getBrandName())
                .modelName(tile.getModelName())
                .color(tile.getColor())
                .qty(tile.getQty())
                .piecesPerBox(tile.getPiecesPerBox())
                .category(tile.getCategory())
                .subCategory(tile.getSubCategory())
                .finishing(tile.getFinishing())
                .minimumStockLevel(tile.getMinimumStockLevel())
                .build();
    }

    public List<TileQtyDto> searchTiles(String search) {
        List<Tile> tiles =  tileDao.searchTiles(search);
        return tiles.stream()
                .map(tile -> {
                    return TileQtyDto.builder()
                            ._id(tile.get_id())
                            .skuCode(tile.getSkuCode())
                            .qty(tile.getQty())
                            .build();
                })
                .toList();
    }
}
