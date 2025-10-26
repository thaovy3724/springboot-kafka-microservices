package com.food.ordering.system.order.service.domain.dto.create;

import com.food.ordering.system.order.service.domain.dto.Address;
import com.food.ordering.system.order.service.domain.dto.Item;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import com.food.ordering.system.order.service.domain.valueobject.DeliveryAddress;

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
