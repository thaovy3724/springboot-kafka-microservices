package com.food.ordering.system.restaurant.domain.core.entity;

import com.food.ordering.system.restaurant.domain.core.valueobject.OrderApprovalStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderApproval {
    // Immutable fields
    private final UUID id;
    private final UUID restaurantId;
    private final UUID orderId;
    private OrderApprovalStatus orderApprovalStatus;

    public void approve() {
       this.orderApprovalStatus = OrderApprovalStatus.APPROVED;
    }

    public void reject() {
       this.orderApprovalStatus = OrderApprovalStatus.REJECTED;
    }

}
