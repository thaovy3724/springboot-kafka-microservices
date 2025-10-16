package com.swa.order_service.order_domain.order_domain_core.valueobject;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryAddress {
    private final String street;
    private final String postalCode;
    private final String city;
}
