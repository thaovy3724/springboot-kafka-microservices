package com.swa.payment_service.order_domain.order_domain_core.exception;

public class PaymentNotFoundException extends DomainException{
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
