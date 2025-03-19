package com.trustrace.tiles_hub_be.payment;


import com.razorpay.*;
import com.trustrace.tiles_hub_be.billing.BillingCycleService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RazorpayService {

    @Autowired
    private BillingCycleService billingCycleService;

    @Value("${razorpay.key_id}")
    private String razorpayKey;

    @Value("${razorpay.key_secret}")
    private String razorpaySecret;

    public String createPaymentLink(String billId, double amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKey, razorpaySecret);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount * 100);
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("description", "Api Usage");

        PaymentLink payment;

        try {
            payment = razorpay.paymentLink.create(paymentLinkRequest);
            String paymentLinkId = payment.get("id");
            billingCycleService.updatePaymentLinkId(billId, paymentLinkId);
            String paymentLink = payment.get("short_url");
            billingCycleService.updatePaymentLink(billId, paymentLink);
            return paymentLink;
        } catch (RazorpayException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }
}