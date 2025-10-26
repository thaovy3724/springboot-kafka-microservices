package com.food.ordering.system.restaurant.domain.application.port.output;

import com.food.ordering.system.restaurant.domain.core.entity.OrderApproval;

public interface OrderApprovalRepos {
    OrderApproval save(OrderApproval orderApproval);
}
