package com.trustrace.tiles_hub_be.payment;

import com.razorpay.RazorpayException;
import com.trustrace.tiles_hub_be.billing.BillingCycle;
import com.trustrace.tiles_hub_be.billing.BillingCycleService;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired BillingCycleService billingCycleService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> getPaymentLink(
            @PathVariable String id,
            @RequestParam(name = "amount", defaultValue = "0") Double amount
    ) throws RazorpayException {
        String paymentLink = paymentService.getPaymentLink(id, amount);
        return ResponseEntity.ok(ResponseUtil.success("Payment Link : ", paymentLink, null));
    }

    @PostMapping("/webhook")
    public void  getPaymentDetails(
            @RequestBody Map<String, Object> object
    ) {
        System.out.println(object);
        Map<String, Object> payload = (Map<String, Object>) object.get("payload");
        Map<String, Object> paymentData = (Map<String, Object>) payload.get("payment");
        Map<String, Object> paymentEntity = (Map<String, Object>) paymentData.get("entity");

        Map<String, Object> paymentLinkData = (Map<String, Object>) payload.get("payment_link");

        Map<String, Object> paymentLinkEntity = (Map<String, Object>) paymentLinkData.get("entity");


        String status = (String) ((Map<String, Object>) ((Map<String, Object>) payload.get("order")).get("entity")).get("status");

        System.out.println(status);

        BillingCycle billingCycle = billingCycleService.findByPaymentId(paymentLinkEntity.get("id"));
        if (status.equals("paid")) {
            System.out.println("at paid");
            billingCycle.setPayment(true);
            billingCycleService.save(billingCycle);
        }


    }
}
