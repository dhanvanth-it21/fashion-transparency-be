package com.trustrace.fashion_transparency_be.billing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "billing_cycle")
public class BillingCycle {

    @Id
    private String _id;
    private Date billingCycleStart;
    private Date billingCycleEnd;
    private Map<String, ApiUsageDetails> apiUsage;
    private String paymentLinkId;
    private String paymentLink;
    private boolean payment;
    private boolean active;
}
