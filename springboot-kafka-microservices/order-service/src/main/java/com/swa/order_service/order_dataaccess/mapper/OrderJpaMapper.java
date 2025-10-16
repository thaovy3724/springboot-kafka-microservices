package com.swa.order_service.order_dataaccess.mapper;

import com.swa.order_service.order_dataaccess.entity.OrderAddressEntity;
import com.swa.order_service.order_dataaccess.entity.OrderEntity;
import com.swa.order_service.order_dataaccess.entity.OrderItemEntity;
import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.entity.OrderItem;
import com.swa.order_service.order_domain.order_domain_core.valueobject.DeliveryAddress;
import com.swa.order_service.order_domain.order_domain_core.valueobject.Money;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
// Mapping: Domain Entity <-> JPA Entity
public class OrderJpaMapper {
    public OrderEntity orderToOrderEntity(Order order){
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getOrderId())
                .customerId(order.getCustomerId())
                .restaurantId(order.getRestaurantId())
                .trackingId(order.getTrackingId())
                .price(order.getPrice().getAmount())
                .address(deliveryAddressToOrderAddressEntity(order.getDeliveryAddress()))
                .items(orderItemsToOrderItemsEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(";", order.getFailureMessages()) : "")
                .createdAt(order.getCreatedAt())
                .build();

        // bidirectional relationship
        // Order <-> OrderAddress
        orderEntity.getAddress().setOrder(orderEntity);
        // Order <-> OrderItem
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    private List<OrderItemEntity> orderItemsToOrderItemsEntities(List<OrderItem> orderItems) {
        return orderItems.stream().map(orderItem ->
                        OrderItemEntity.builder()
                                .id(orderItem.getId())
                                .productId(orderItem.getProductId())
                                .quantity(orderItem.getQuantity())
                                .price(orderItem.getPrice().getAmount())
                                .subTotal(orderItem.getSubTotal().getAmount())
                                .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToOrderAddressEntity(DeliveryAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(UUID.randomUUID())
                .city(deliveryAddress.getCity())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .build();
    }

    public Order orderEntityToOrder(OrderEntity orderEntity){
        return Order.builder()
                .orderId(orderEntity.getId())
                .customerId(orderEntity.getCustomerId())
                .restaurantId(orderEntity.getRestaurantId())
                .trackingId(orderEntity.getTrackingId())
                .failureMessages(!orderEntity.getFailureMessages().isEmpty() ?
                        Arrays.asList(orderEntity.getFailureMessages().split(";"))
                        : new ArrayList<>())
                .orderStatus(orderEntity.getOrderStatus())
                .deliveryAddress(addressToDeliveryAddress(orderEntity.getAddress()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream().map(orderItemEntity ->
                        OrderItem.builder()
                                .id(orderItemEntity.getId())
                                .orderId(orderItemEntity.getOrder().getId())
                                .productId(orderItemEntity.getProductId())
                                .quantity(orderItemEntity.getQuantity())
                                .price(new Money(orderItemEntity.getPrice()))
                                .subTotal(new Money(orderItemEntity.getSubTotal()))
                                .build()
                )
                .collect(Collectors.toList());
    }

    private DeliveryAddress addressToDeliveryAddress(OrderAddressEntity address) {
        return DeliveryAddress.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .build();
    }


}

