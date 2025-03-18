package com.trustrace.tiles_hub_be.billing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingCycleDetailDto {

    private String _id;
    private List<RequestDetails> usageList;
}
