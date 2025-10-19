package com.swa.order_service.order_domain.order_application_service.dto.create;

import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Builder
public class CreateOrderResponse {
    private final UUID orderTrackingId;
    private final OrderStatus orderStatus;
    private final String message;
}

