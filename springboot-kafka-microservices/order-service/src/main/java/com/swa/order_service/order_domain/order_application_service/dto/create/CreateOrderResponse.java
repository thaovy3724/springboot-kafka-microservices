package com.swa.order_service.order_domain.order_application_service.dto.create;

import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Getter
@Builder
public class CreateOrderResponse {
    @NotNull
    private final UUID orderTrackingId;
    @NotNull
    private final OrderStatus orderStatus;
    @NotNull
    private final String message;
}

