package com.trustrace.tiles_hub_be.builder.tile;

import com.trustrace.tiles_hub_be.model.collections.tile.Finishing;
import com.trustrace.tiles_hub_be.model.collections.tile.TileCategory;
import com.trustrace.tiles_hub_be.model.collections.tile.TileSize;
import com.trustrace.tiles_hub_be.model.collections.tile.TileSubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TileQtyDto {

    private String _id;
    private String skuCode;
    private int qty;
}
