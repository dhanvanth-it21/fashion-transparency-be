package com.trustrace.tiles_hub_be.billing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDetails {
    private String method;
    private int count;
    private double pricePerRequest;
    private double totalAmount;
}
