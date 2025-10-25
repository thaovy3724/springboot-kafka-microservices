package com.food.ordering.system.order.service.domain.dto.create;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import com.food.ordering.system.order.service.domain.valueobject.OrderStatus;

import java.util.UUID;

@Getter
@Builder
public class CreateOrderResponse {
    private final UUID orderTrackingId;
    private final OrderStatus orderStatus;
    private final String message;
}
