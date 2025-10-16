package com.swa.order_service.order_domain.order_application_service.mapper;

import com.swa.order_service.order_domain.order_application_service.dto.Address;
import com.swa.order_service.order_domain.order_application_service.dto.OrderItem;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderResponse;
import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.valueobject.DeliveryAddress;
import com.swa.order_service.order_domain.order_domain_core.valueobject.Money;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// Mapping: DTO <-> Domain Entity
public class OrderDataMapper {
    public Order createOrderCommandToOrder(CreateOrderCommand command) {
        return Order.builder()
                .customerId(command.getCustomerId())
                .restaurantId(command.getRestaurantId())
                .price(new Money(command.getPrice()))
                .deliveryAddress(addressToDeliveryAddress(command.getAddress()))
                .items(orderItemsToOrderItemEntities(command.getItems()))
                .build();
    }

    private List<com.swa.order_service.order_domain.order_domain_core.entity.OrderItem> orderItemsToOrderItemEntities(
            @NotNull List<OrderItem> orderItems) {
        return orderItems.stream().map(
                        orderItem ->
                                com.swa.order_service.order_domain.order_domain_core.entity.OrderItem.builder()
                                        .productId(orderItem.getProductId())
                                        .quantity(orderItem.getQuantity())
                                        .price(new Money(orderItem.getPrice()))
                                        .subTotal(new Money(orderItem.getSubTotal()))
                                        .build())
                .collect(Collectors.toList());
    }

    private DeliveryAddress addressToDeliveryAddress(@NotNull Address address) {
        return DeliveryAddress.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message){
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

}

