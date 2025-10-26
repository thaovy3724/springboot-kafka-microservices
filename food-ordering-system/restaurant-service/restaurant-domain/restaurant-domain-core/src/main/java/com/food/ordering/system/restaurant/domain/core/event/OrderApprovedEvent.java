package com.food.ordering.system.restaurant.domain.core.event;

import com.food.ordering.system.restaurant.domain.core.entity.OrderApproval;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class OrderApprovedEvent extends OrderApprovalEvent{
    public OrderApprovedEvent(
            UUID restaurantId,
            OrderApproval orderApproval,
            List<String> failureMessages,
            ZonedDateTime createdAt) {
        super(orderApproval, restaurantId, failureMessages, createdAt);
    }
}
