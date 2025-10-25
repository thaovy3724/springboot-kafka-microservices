package com.food.ordering.system.order.service.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class Item {
    @NotNull
    private final UUID productId;
    private final int quantity;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final BigDecimal subTotal;
}
