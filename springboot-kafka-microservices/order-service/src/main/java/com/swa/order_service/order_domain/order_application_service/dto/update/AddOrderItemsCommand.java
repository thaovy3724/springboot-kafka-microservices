package com.swa.order_service.order_domain.order_application_service.dto.update;

import com.swa.order_service.order_domain.order_application_service.dto.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AddOrderItemsCommand {
    private final UUID trackingId;
    private final List<Item> items;
}
