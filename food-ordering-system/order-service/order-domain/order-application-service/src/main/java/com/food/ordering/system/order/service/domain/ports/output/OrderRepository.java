package com.food.ordering.system.order.service.domain.ports.output;

import com.food.ordering.system.order.service.domain.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);
    List<Order> getOrderByCustomerId(UUID customerId);
    long getTotalOrders();
    BigDecimal calculateTotalRevenue();
    BigDecimal calculateAvgOrderValue();
    Optional<Order> findByTrackingId(UUID trackingId);
}
