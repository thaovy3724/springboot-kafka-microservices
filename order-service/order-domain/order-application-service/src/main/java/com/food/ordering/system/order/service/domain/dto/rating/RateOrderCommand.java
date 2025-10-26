package com.food.ordering.system.order.service.domain.dto.rating;

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
