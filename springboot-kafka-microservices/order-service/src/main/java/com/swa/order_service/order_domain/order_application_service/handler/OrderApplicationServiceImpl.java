package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.ports.input.OrderApplicationService;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final CreateOrderHandler createOrderHandler;

    public OrderApplicationServiceImpl(CreateOrderHandler createOrderHandler) {
        this.createOrderHandler = createOrderHandler;
    }
//    private final TrackOrderHandler trackOrderHandler;
//    private final CancelOrderHandler cancelOrderHandler;

    // Use Case: Create order
    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand command){
        return createOrderHandler.createOrder(command);
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

