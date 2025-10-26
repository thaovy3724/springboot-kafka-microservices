package com.food.ordering.system.order.service.domain.dto.history;

import com.food.ordering.system.order.service.domain.dto.Address;
import com.food.ordering.system.order.service.domain.dto.Item;
import com.food.ordering.system.order.service.domain.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class HistoryOrderResponse {
    private final UUID trackingId;
    private final UUID restaurantId;
    private final BigDecimal price;
    private final List<Item> items;
    private final Address address;
    private final OrderStatus orderStatus;
    private final List<String> failureMessage;
    private final ZonedDateTime createdAt;
}
