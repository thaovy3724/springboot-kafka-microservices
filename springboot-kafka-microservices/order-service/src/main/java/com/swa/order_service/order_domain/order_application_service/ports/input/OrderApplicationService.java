package com.swa.order_service.order_domain.order_application_service.ports.input;

import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.history.HistoryOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.statistics.StatisticsResponse;
import com.swa.order_service.order_domain.order_application_service.dto.update.add.UpdateAddItemsCommand;
import com.swa.order_service.order_domain.order_application_service.dto.update.add.UpdateAddItemsResponse;
import com.swa.order_service.order_domain.order_application_service.dto.update.delete.UpdateDelItemsCommand;
import com.swa.order_service.order_domain.order_application_service.dto.update.delete.UpdateDelItemsResponse;

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

    UpdateAddItemsResponse updateAddItems(UpdateAddItemsCommand command);
    UpdateDelItemsResponse updateDeleteItems(UpdateDelItemsCommand command);
//    //UseCase:Trackorder
//    TrackOrderResponsetrackOrder(TrackOrderQueryquery);
//    //UseCase:Cancelorder
//    CancelOrderResponsecancelOrder(CancelOrderCommandcommand);
}
