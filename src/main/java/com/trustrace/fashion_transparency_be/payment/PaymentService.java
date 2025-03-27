package com.trustrace.fashion_transparency_be.payment;

import com.razorpay.RazorpayException;
import com.trustrace.fashion_transparency_be.billing.BillingCycle;
import com.trustrace.fashion_transparency_be.billing.BillingCycleService;
import com.trustrace.fashion_transparency_be.exceptionHandlers.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private BillingCycleService billingCycleService;

    @Autowired
    private RazorpayService razorpayService;

    public String getPaymentLink(String id, Double amount) throws RazorpayException {

        BillingCycle billingCycle = billingCycleService.getBillingCycle(id).orElseThrow(() -> new ResourceNotFoundException("No bill found"));
        return razorpayService.createPaymentLink(id, amount);
    }
}
