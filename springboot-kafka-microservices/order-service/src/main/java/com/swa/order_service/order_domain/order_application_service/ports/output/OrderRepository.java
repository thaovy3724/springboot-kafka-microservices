package com.swa.order_service.order_domain.order_application_service.ports.output;

import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderRating;

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
//    //FindbyID
//    Optional<Order>findById(OrderIdorderId);
//
    //Find by tracking ID
    Optional<Order> findByTrackingId(UUID trackingId);

    // Update rating
    int updateRatingByTrackingId(UUID trackingId, OrderRating orderRating);
}
