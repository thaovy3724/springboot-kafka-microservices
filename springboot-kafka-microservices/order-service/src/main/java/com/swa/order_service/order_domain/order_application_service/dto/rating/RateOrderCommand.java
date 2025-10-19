package com.swa.order_service.order_domain.order_application_service.dto.rating;

import com.swa.order_service.order_domain.order_application_service.dto.Rating;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Builder
public class RateOrderCommand {
    @NotNull
    private final UUID trackingId;
    private final Rating rating;
}
