package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.history.HistoryOrderResponse;
import com.swa.order_service.order_domain.order_application_service.mapper.OrderDataMapper;
import com.swa.order_service.order_domain.order_application_service.ports.output.OrderRepository;
import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.exception.OrderNotFoundException;
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
