package com.swa.order_service.order_domain.order_application_service.dto.update.delete;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class UpdateDelItemsCommand {
    @NotNull
    private final UUID trackingId;
    private final List<UUID> productIds;
}
