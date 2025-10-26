package com.food.ordering.system.restaurant.domain.application.dto;

import com.food.ordering.system.restaurant.domain.core.entity.Product;
import com.food.ordering.system.restaurant.domain.core.valueobject.RestaurantOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RestaurantApprovalReq {
    private String id;
    private String restaurantId;
    private String orderId;
    private RestaurantOrderStatus restaurantOrderStatus;
    private List<Product> products;
    private java.math.BigDecimal price;
    private java.time.Instant createdAt;
}
