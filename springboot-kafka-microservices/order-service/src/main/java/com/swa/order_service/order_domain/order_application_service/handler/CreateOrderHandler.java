package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.mapper.OrderDataMapper;
import com.swa.order_service.order_domain.order_application_service.ports.output.OrderRepository;
import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public CreateOrderHandler(OrderDataMapper mapper, OrderRepository repos) {
        this.mapper = mapper;
        this.repository = repos;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand){
        // Map the createOrderCommand DTO to Order entity
        Order order = mapper.createOrderCommandToOrder(createOrderCommand);
        // [Optional] Call interface methods relate to database (ex: isCustomerExist())

        // Business Logic
        order.initializeAndValidateOrder();
        Order orderSaved = repository.save(order);
         if(orderSaved == null)
             throw new OrderDomainException("Order can not be saved");

        // Return Response DTO
        return mapper.orderToCreateOrderResponse(orderSaved, "Create order successfully!");
    }
}

