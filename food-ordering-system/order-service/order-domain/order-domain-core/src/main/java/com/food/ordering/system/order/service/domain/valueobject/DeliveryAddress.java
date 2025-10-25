package com.food.ordering.system.order.service.domain.valueobject;

import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DeliveryAddress {
    // immutable
    private final String street;
    private final String postalCode;
    private final String city;

    // mutable
    private UUID id;

    public void validateDeliveryAddress() throws OrderDomainException {
        if(street == null || street.isEmpty())
            throw new OrderDomainException("Street cannot be null or empty");

        if(city == null || city.isEmpty())
            throw new OrderDomainException("City cannot be null or empty");

        if(postalCode == null || postalCode.isEmpty())
            throw new OrderDomainException("Postal code must has 5 number");
    }
}
