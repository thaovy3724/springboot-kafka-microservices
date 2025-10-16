package com.swa.order_service.order_domain.order_application_service.dto;

import lombok.Builder;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class OrderItem {
    @NotNull
    private final UUID productId;
    private final int quantity;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final BigDecimal subTotal;
}
