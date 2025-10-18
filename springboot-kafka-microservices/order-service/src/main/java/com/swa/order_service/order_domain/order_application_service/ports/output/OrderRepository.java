package com.swa.order_service.order_domain.order_application_service.ports.output;

import com.swa.order_service.order_domain.order_domain_core.entity.Order;

public interface OrderRepository {
    Order save(Order order);

//    //FindbyID
//    Optional<Order>findById(OrderIdorderId);
//
//    //FindbytrackingID
//    Optional<Order>findByTrackingId(TrackingIdtrackingId);
}
