package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.update.AddOrderItemsCommand;
import com.swa.order_service.order_domain.order_application_service.dto.update.AddOrderItemsResponse;
import com.swa.order_service.order_domain.order_application_service.mapper.OrderDataMapper;
import com.swa.order_service.order_domain.order_application_service.ports.output.OrderRepository;

public class UpdateOrderHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public UpdateOrderHandler(OrderDataMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

}

