package com.food.ordering.system.payment.service.domain.rest;

import com.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "payments", produces = "application/vnd.api.v1+json")
public class PaymentServiceController {
    private final PaymentRequestMessageListener paymentRequestMessageListener;

    public PaymentServiceController(PaymentRequestMessageListener paymentRequestMessageListener) {
        this.paymentRequestMessageListener = paymentRequestMessageListener;
    }

    @PostMapping
    public PaymentResponse createPayment() {

    }
}
