package com.food.ordering.system.order.service.domain.handler;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.OrderRepository;
import org.springframework.stereotype.Component;
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