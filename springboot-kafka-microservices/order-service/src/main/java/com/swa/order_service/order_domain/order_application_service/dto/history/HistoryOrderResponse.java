package com.swa.order_service.order_domain.order_application_service.dto.history;

import com.swa.order_service.order_domain.order_application_service.dto.Address;
import com.swa.order_service.order_domain.order_application_service.dto.Item;
import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderStatus;
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
