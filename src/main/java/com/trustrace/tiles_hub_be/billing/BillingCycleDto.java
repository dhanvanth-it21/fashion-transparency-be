package com.trustrace.tiles_hub_be.billing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingCycleDto {

    private String _id;
    private Date billingCycleStart;
    private Date billingCycleEnd;
    private double totalAmount;
    private boolean payment;
}
