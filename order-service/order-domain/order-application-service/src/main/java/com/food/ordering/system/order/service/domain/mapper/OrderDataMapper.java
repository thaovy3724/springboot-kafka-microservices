package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.order.service.domain.dto.Address;
import com.food.ordering.system.order.service.domain.dto.Item;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.history.HistoryOrderResponse;
import com.food.ordering.system.order.service.domain.dto.rating.RateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.rating.Rating;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.valueobject.OrderRating;
import org.jetbrains.annotations.NotNull;
import com.food.ordering.system.order.service.domain.valueobject.DeliveryAddress;
import com.food.ordering.system.order.service.domain.valueobject.Money;
import org.springframework.stereotype.Component;
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
                .items(itemsToOrderItems(command.getItems()))
                .build();
    }

    private List<OrderItem> itemsToOrderItems(
            @NotNull List<Item> orderItems) {
        return orderItems.stream().map(
                        orderItem ->
                                OrderItem.builder()
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

    private List<Item> orderItemsToItems(@NotNull List<OrderItem> orderItems){
        return orderItems.stream().map(
                        orderItem -> Item.builder()
                                .productId(orderItem.getProductId())
                                .quantity(orderItem.getQuantity())
                                .price(orderItem.getPrice().getAmount())
                                .subTotal(orderItem.getSubTotal().getAmount())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private Address deliveryAddressToAddress(DeliveryAddress deliveryAddress){
        return Address.builder()
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .city(deliveryAddress.getCity())
                .build();
    }

    public HistoryOrderResponse orderToHistoryOrderResponse(Order order){
        return HistoryOrderResponse.builder()
                .trackingId(order.getTrackingId())
                .restaurantId(order.getRestaurantId())
                .price(order.getPrice().getAmount())
                .items(orderItemsToItems(order.getItems()))
                .address(deliveryAddressToAddress(order.getDeliveryAddress()))
                .orderStatus(order.getOrderStatus())
                .failureMessage(order.getFailureMessages())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public OrderRating ratingToOrderRating(Rating rating){
        return OrderRating.builder()
                .star(rating.getStar())
                .comment(rating.getComment())
                .build();
    }

    private Rating orderRatingToRating(OrderRating orderRating){
        return Rating.builder()
                .star(orderRating.getStar())
                .comment(orderRating.getComment())
                .build();
    }

    public RateOrderResponse toRateOrderResponse(Order order, String message){
        return RateOrderResponse.builder()
                .trackingId(order.getTrackingId())
                .rating(orderRatingToRating(order.getRating()))
                .message(message)
                .build();
    }
}

