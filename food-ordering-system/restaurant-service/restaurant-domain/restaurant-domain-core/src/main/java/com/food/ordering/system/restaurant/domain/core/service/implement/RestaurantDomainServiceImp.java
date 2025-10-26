package com.food.ordering.system.restaurant.domain.core.service.implement;

import com.food.ordering.system.restaurant.domain.core.entity.Restaurant;
import com.food.ordering.system.restaurant.domain.core.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.domain.core.event.OrderApprovedEvent;
import com.food.ordering.system.restaurant.domain.core.event.OrderRejectedEvent;
import com.food.ordering.system.restaurant.domain.core.service.RestaurantDomainService;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class RestaurantDomainServiceImp implements RestaurantDomainService {

    @Override
    public OrderApprovalEvent approveOrder(Restaurant restaurant, List<String> failureMessages) {
        restaurant.validateOrder(failureMessages);
//        log.info("Validating order with id: {}", restaurant.getOrderDetail().getOrderId()));
        System.out.println("Validating order with id: " + restaurant.getOrderDetail().getOrderId());

        if (failureMessages.isEmpty()) {
//            log.info("Order is approved for order id: {}", restaurant.getOrderDetail().getOrderId());
            System.out.println("Order is approved for order id: " + restaurant.getOrderDetail().getOrderId());
            restaurant.approveOrder();
            return new OrderApprovedEvent(
                    restaurant.getRestaurantId(),
                    restaurant.getOrderApproval(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of("UTC")));
        } else {
//            log.info("Order is rejected for order id: {}", restaurant.getOrderDetail().getOrderId());
            System.out.println("Order is rejected for order id: " + restaurant.getOrderDetail().getOrderId());
            restaurant.approveOrder();
            return new OrderRejectedEvent(
                    restaurant.getOrderApproval(),
                    restaurant.getRestaurantId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of("UTC")));
        }
    }
}
