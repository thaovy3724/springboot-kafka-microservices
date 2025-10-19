package com.swa.order_service.order_domain.order_domain_core.exception;

public class OrderNotFoundException extends DomainException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}
