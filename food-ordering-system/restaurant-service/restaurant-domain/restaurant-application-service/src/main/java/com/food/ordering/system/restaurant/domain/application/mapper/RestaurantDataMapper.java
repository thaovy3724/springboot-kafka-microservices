package com.food.ordering.system.restaurant.domain.application.mapper;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.restaurant.domain.application.dto.RestaurantApprovalReq;
import com.food.ordering.system.restaurant.domain.core.entity.OrderDetail;
import com.food.ordering.system.restaurant.domain.core.entity.Product;
import com.food.ordering.system.restaurant.domain.core.entity.Restaurant;
import org.springframework.stereotype.Service;
import lombok.Builder;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Builder
public class RestaurantDataMapper {
    public Restaurant restaurantApprovalRequestToRestaurant(
            RestaurantApprovalReq restaurantApprovalRequest
    ) {
        return Restaurant.builder()
                .restaurantId(UUID.fromString(restaurantApprovalRequest.getRestaurantId()))
                .orderDetail(OrderDetail.builder()
                        .orderId(UUID.fromString(restaurantApprovalRequest.getOrderId()))
                        .products(restaurantApprovalRequest.getProducts().stream().map(
                                        product -> Product.builder()
                                                .id(product.getId())
                                                .quantity(product.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .price(new Money(restaurantApprovalRequest.getPrice()))
                        .orderStatus(
                                OrderStatus.valueOf(
                                        restaurantApprovalRequest.getRestaurantOrderStatus().name()
                                )
                        )
                        .build())
                .build();
    }
}
