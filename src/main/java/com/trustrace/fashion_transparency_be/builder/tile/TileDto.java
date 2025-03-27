package com.trustrace.fashion_transparency_be.builder.tile;

import com.trustrace.fashion_transparency_be.model.collections.tile.Finishing;
import com.trustrace.fashion_transparency_be.model.collections.tile.TileCategory;
import com.trustrace.fashion_transparency_be.model.collections.tile.TileSize;
import com.trustrace.fashion_transparency_be.model.collections.tile.TileSubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TileDto {

    private String skuCode;
    private TileSize tileSize;
    private String brandName;
    private String modelName;
    private String color;
    private int qty;
    private int piecesPerBox;
    private TileCategory category;
    private TileSubCategory subCategory;
    private Finishing finishing;
    private int minimumStockLevel;


}
