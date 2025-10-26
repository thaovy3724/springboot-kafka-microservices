package com.food.ordering.system.restaurant.domain.core.service;

import com.food.ordering.system.restaurant.domain.core.entity.Restaurant;
import com.food.ordering.system.restaurant.domain.core.event.OrderApprovalEvent;

import java.util.List;

public interface RestaurantDomainService {
    OrderApprovalEvent approveOrder(Restaurant restaurant, List<String> failureMessages);
}
