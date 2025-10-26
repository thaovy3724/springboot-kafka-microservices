package com.food.ordering.system.payment.service.domain.exception;


public class PaymentNotFoundException extends DomainException{
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
