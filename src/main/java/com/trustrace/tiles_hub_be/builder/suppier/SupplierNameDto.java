package com.trustrace.tiles_hub_be.builder.suppier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierNameDto {
    private String _id;
    private String brandName;
}
