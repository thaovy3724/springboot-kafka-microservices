package com.food.ordering.system.restaurant.domain.core.entity;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.service.domain.valueobject.Money;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class Restaurant {
    // Immutable fields
    private final UUID restaurantId;
    private final OrderDetail orderDetail;

    // Mutable fields
    private OrderApproval orderApproval;

    public void validateOrder(List<String> failureMessages) {
        if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add("Payment is not completed for order: " + orderDetail.getOrderId());
        }
        Money totalAmount = orderDetail.getProducts().stream().map(product -> {
            if (!product.isAvailable()) {
                failureMessages.add("Product with id: " + product.getId()
                        + " is not available");
            }
            return product.getUnitPrice().multiply(product.getQuantity());
        }).reduce(Money.ZERO, Money::add);

        if (!totalAmount.equals(orderDetail.getPrice())) {
            failureMessages.add("Price total is not correct for order: " + orderDetail.getOrderId());
        }
    }

    public void approveOrder() {
        this.orderApproval.approve();
    }

    public void rejectOrder() {
        this.orderApproval.reject();
    }
}
