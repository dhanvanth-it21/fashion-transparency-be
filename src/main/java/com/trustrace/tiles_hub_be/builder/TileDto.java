package com.trustrace.tiles_hub_be.builder;

import com.trustrace.tiles_hub_be.model.collections.tile.Finishing;
import com.trustrace.tiles_hub_be.model.collections.tile.TileCategory;
import com.trustrace.tiles_hub_be.model.collections.tile.TileSize;
import com.trustrace.tiles_hub_be.model.collections.tile.TileSubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TileDto {

    private String skuCode;
    private TileSize tileSize;
    private String brandId;
    private String modelName;
    private String color;
    private int qty;
    private int piecesPerBox;
    private TileCategory category;
    private TileSubCategory subCategory;
    private Finishing finishing;
    private Date createdAt;
    private Date updatedAt;



}
