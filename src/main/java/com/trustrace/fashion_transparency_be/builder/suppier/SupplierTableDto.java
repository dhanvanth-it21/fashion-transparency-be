package com.trustrace.fashion_transparency_be.builder.suppier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierTableDto {
    private String _id;
    private String brandName;
    private String phone;

}
