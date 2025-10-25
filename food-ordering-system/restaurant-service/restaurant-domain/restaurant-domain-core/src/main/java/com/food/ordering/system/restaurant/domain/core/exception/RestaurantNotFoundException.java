package com.food.ordering.system.restaurant.domain.core.exception;

public class RestaurantNotFoundException extends DomainException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
