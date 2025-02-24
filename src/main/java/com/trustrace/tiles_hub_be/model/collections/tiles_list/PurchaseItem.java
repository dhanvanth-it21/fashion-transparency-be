package com.trustrace.tiles_hub_be.model.collections.tiles_list;

import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class PurchaseItem {


    private String tileId; //referencing the particular tile
    private int qty; //number of boxes

}
