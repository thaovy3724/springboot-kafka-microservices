package com.swa.order_service.order_domain.order_application_service.dto.update;

import java.util.UUID;

public class DeleteOrderItemsCommand {
    private final UUID trackingId;

    public DeleteOrderItemsCommand(UUID trackingId) {
        this.trackingId = trackingId;
    }
}
