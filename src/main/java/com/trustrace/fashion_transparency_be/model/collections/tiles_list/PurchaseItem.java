package com.trustrace.fashion_transparency_be.model.collections.tiles_list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseItem {


    private String tileId;
    private int addQty;

}
