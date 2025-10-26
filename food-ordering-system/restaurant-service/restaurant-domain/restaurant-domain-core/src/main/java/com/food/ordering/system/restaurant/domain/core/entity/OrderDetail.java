package com.food.ordering.system.restaurant.domain.core.entity;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.service.domain.valueobject.Money;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class OrderDetail {
    // Immutable fields
    private final UUID orderId;
    private final OrderStatus orderStatus;
    private final Money price;
    private final List<Product> products;
}
