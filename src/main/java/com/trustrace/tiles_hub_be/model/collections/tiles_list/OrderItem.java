package com.trustrace.tiles_hub_be.model.collections.tiles_list;

import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    private String tileId;
    private int requiredQty;

}
