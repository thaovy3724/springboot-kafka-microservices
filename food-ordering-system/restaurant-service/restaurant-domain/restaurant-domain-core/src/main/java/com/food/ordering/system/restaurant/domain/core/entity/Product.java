package com.food.ordering.system.restaurant.domain.core.entity;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.restaurant.domain.core.exception.RestaurantDomainException;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Product {
    // Immutable fields
    private final int quantity;
    private final UUID id;

    // Mutable fields
    private String name;
    private Money unitPrice;
    private boolean available;

    public void validateQuantity() {
        if (this.quantity < 0) throw new RestaurantDomainException("Product quantity is not valid");
    }

    public void validateUnitPrice() {
        if (!this.unitPrice.isGreaterThanZero()) throw new RestaurantDomainException("Unit price is not valid");
    }

}
