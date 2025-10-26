package com.food.ordering.system.order.service.domain.exception;

public class OrderNotFoundException extends DomainException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}
