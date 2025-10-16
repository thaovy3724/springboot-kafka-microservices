package com.swa.order_service.order_domain.order_application_service.dto.create;

import com.swa.order_service.order_domain.order_application_service.dto.Address;
import com.swa.order_service.order_domain.order_application_service.dto.OrderItem;
import com.swa.order_service.order_domain.order_domain_core.valueobject.DeliveryAddress;
import lombok.Builder;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

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
    private final List<OrderItem> items;
    @NotNull
    private final Address address;
}

