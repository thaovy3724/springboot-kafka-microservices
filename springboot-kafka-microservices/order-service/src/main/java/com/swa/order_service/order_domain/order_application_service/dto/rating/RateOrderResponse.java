package com.swa.order_service.order_domain.order_application_service.dto.rating;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class RateOrderResponse {
    public final UUID trackingId;
    public final Rating rating;
    public final String message;
}
