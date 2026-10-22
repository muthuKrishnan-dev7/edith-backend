package com.backend.Payments;

import com.backend.Payments.DTOs.RequestCreatePayment;
import com.backend.Payments.DTOs.ResponsePayment;
import com.razorpay.RazorpayException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentsService;

    public PaymentController(PaymentService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @RequestMapping("/create-payment")
    public ResponseEntity<ResponsePayment> createPayment(@RequestBody @Valid RequestCreatePayment request) throws RazorpayException {
        ResponsePayment response = paymentsService.getPaymentDetails(request);
        return ResponseEntity.ok().body(response);
    }


}
