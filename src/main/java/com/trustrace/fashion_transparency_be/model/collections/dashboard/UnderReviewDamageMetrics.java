package com.trustrace.fashion_transparency_be.model.collections.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnderReviewDamageMetrics {
    private int warehouse;
    private int retailShop;
    private int manufacturer;
}
