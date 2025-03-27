package com.trustrace.fashion_transparency_be.billing;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ApiUsageDetails {
    private int count;
    private double pricePerRequest;
}
