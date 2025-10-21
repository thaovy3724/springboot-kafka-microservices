package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.history.HistoryOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.statistics.StatisticsResponse;
import com.swa.order_service.order_domain.order_application_service.dto.update.AddOrderItemsCommand;
import com.swa.order_service.order_domain.order_application_service.dto.update.AddOrderItemsResponse;
import com.swa.order_service.order_domain.order_application_service.ports.input.OrderApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final CreateOrderHandler createOrderHandler;
    private final HistoryOrderHandler historyOrderHandler;
    private final StatisticsHandler statisticsHandler;
    private final RateOrderHandler rateOrderHandler;

    public OrderApplicationServiceImpl(CreateOrderHandler createOrderHandler, HistoryOrderHandler historyOrderHandler, StatisticsHandler statisticsHandler, RateOrderHandler rateOrderHandler) {
        this.createOrderHandler = createOrderHandler;
        this.historyOrderHandler = historyOrderHandler;
        this.statisticsHandler = statisticsHandler;
        this.rateOrderHandler = rateOrderHandler;
    }
//    private final TrackOrderHandler trackOrderHandler;
//    private final CancelOrderHandler cancelOrderHandler;

    // Use Case: Create order
    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand command){
        return createOrderHandler.createOrder(command);
    }

    // Use Case: getOrderByCustomerId
    @Override
    public List<HistoryOrderResponse> getOrderByCustomerId(UUID customerId){
        return historyOrderHandler.getOrderByCustomerId(customerId);
    }

    // UseCase: Get statistic
    @Override
    public StatisticsResponse getStatistics(){
        return statisticsHandler.getStatistics();
    }

    // UseCase: Rate order
    @Override
    public RateOrderResponse rateOrder(RateOrderCommand command){
        return rateOrderHandler.rateOrder(command);
    }

    // UseCase: Add order items
    AddOrderItemsResponse updateAddOrderItems(AddOrderItemsCommand command){
        return
    }
    // Use Case: Track order
//    @Override
//    TrackOrderResponse trackOrder(TrackOrderQuery query){
//
//    }
//    // Use Case: Cancel order
//    @Override
//    CancelOrderResponse cancelOrder(CancelOrderCommand command){
//
//    }
}

