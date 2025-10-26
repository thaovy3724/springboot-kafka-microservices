package com.food.ordering.system.order.service.domain.handler;

import com.food.ordering.system.order.service.domain.dto.history.HistoryOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HistoryOrderHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public HistoryOrderHandler(OrderDataMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<HistoryOrderResponse> getOrderByCustomerId(UUID customerId){
        List<Order> orders = repository.getOrderByCustomerId(customerId);
        if(orders.isEmpty())
            throw new OrderNotFoundException("Order not found");

        return orders.stream().map(order ->
                        mapper.orderToHistoryOrderResponse(order))
                .collect(Collectors.toList());
    }
}
