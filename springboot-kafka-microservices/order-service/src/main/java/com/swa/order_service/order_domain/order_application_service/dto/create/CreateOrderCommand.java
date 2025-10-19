package com.swa.order_service.order_domain.order_application_service.dto.create;

import com.swa.order_service.order_domain.order_application_service.dto.Address;
import com.swa.order_service.order_domain.order_application_service.dto.Item;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CreateOrderCommand {
    @NotNull
    private final UUID customerId;
    @NotNull
    private final UUID restaurantId;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final List<Item> items;
    @NotNull
    private final Address address;
}

