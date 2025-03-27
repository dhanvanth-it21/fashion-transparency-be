package com.trustrace.fashion_transparency_be.builder.tile;

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
