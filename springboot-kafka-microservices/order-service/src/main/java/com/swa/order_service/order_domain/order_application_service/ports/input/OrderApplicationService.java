package com.swa.order_service.order_domain.order_application_service.ports.input;

import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderResponse;

public interface OrderApplicationService {
    //UseCase:Createorder
    CreateOrderResponse createOrder(CreateOrderCommand command);
//    //UseCase:Trackorder
//    TrackOrderResponsetrackOrder(TrackOrderQueryquery);
//    //UseCase:Cancelorder
//    CancelOrderResponsecancelOrder(CancelOrderCommandcommand);
}
