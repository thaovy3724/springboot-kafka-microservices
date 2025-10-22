package com.swa.order_service.order_domain.order_application_service.dto.update.delete;

import com.swa.order_service.order_domain.order_application_service.dto.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class UpdateDelItemsResponse {
    public final UUID trackingId;
    public final List<Item> items;
    public final String message;
}
