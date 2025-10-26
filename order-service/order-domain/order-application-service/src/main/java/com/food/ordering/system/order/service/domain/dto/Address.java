package com.food.ordering.system.order.service.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
public class Address {
    @NotNull
    private final String street;
    @NotNull
    private final String postalCode;
    @NotNull
    private final String city;
}
