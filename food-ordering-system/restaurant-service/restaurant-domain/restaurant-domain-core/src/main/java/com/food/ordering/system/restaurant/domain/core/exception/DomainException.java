package com.food.ordering.system.restaurant.domain.core.exception;

public class DomainException extends RuntimeException{
    public DomainException(String message){
        super(message);
    }
}
