package com.food.ordering.system.restaurant.domain.application.handler;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@Slf4j
public class ApproveOrderHandler {
    // Không dám nhét cái hàm này ở handler, nó gây ra lỗi circular dependency

//    public OrderApprovalEvent approveOrder(Restaurant restaurant, List<String> failureMessages) {
//        restaurant.validateOrder(failureMessages);
////        log.info("Validating order with id: {}", restaurant.getOrderDetail().getOrderId()));
//        System.out.println("Validating order with id: " + restaurant.getOrderDetail().getOrderId()));
//
//        if (failureMessages.isEmpty()) {
////            log.info("Order is approved for order id: {}", restaurant.getOrderDetail().getOrderId());
//            System.out.println("Order is approved for order id: " + restaurant.getOrderDetail().getOrderId());
//            restaurant.approveOrder();
//            return new OrderApprovedEvent(
//                    restaurant.getRestaurantId(),
//                    restaurant.getOrderApproval(),
//                    failureMessages,
//                    ZonedDateTime.now(ZoneId.of("UTC")));
//        } else {
////            log.info("Order is rejected for order id: {}", restaurant.getOrderDetail().getOrderId());
//            System.out.println("Order is rejected for order id: " + restaurant.getOrderDetail().getOrderId());
//            restaurant.approveOrder();
//            return new OrderRejectedEvent(
//                    restaurant.getOrderApproval(),
//                    restaurant.getRestaurantId(),
//                    failureMessages,
//                    ZonedDateTime.now(ZoneId.of("UTC")));
//        }
//    }

}
