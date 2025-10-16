package com.swa.order_service.order_domain.order_domain_core.exception;

public class DomainException extends RuntimeException{
    public DomainException(String message){
        super(message);
    }
}
