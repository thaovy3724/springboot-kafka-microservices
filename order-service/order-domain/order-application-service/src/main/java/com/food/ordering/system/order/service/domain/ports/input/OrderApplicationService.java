package com.food.ordering.system.order.service.domain.ports.input;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.history.HistoryOrderResponse;
import com.food.ordering.system.order.service.domain.dto.rating.RateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.rating.RateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.statistics.StatisticsResponse;

import java.util.List;
import java.util.UUID;

public interface OrderApplicationService {
    // UseCase: Create order
    CreateOrderResponse createOrder(CreateOrderCommand command);
    // UseCase: Get order history
    List<HistoryOrderResponse> getOrderByCustomerId(UUID customerId);
    // UseCase: Get statistic
    StatisticsResponse getStatistics();
    // UseCase: Rate order
    RateOrderResponse rateOrder(RateOrderCommand command);

//    //UseCase:Trackorder
//    TrackOrderResponsetrackOrder(TrackOrderQueryquery);
//    //UseCase:Cancelorder
//    CancelOrderResponsecancelOrder(CancelOrderCommandcommand);
}

