package com.food.ordering.system.order.service.domain.handler;

import com.food.ordering.system.order.service.domain.dto.rating.RateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.rating.RateOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.OrderRepository;
import com.food.ordering.system.order.service.domain.valueobject.OrderRating;
import org.springframework.stereotype.Service;

@Service
public class RateOrderHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public RateOrderHandler(OrderDataMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public RateOrderResponse rateOrder(RateOrderCommand command){
        // Find order by tracking_id
        Order order = repository.findByTrackingId(command.getTrackingId())
                .orElseThrow(()->
                        new OrderNotFoundException("Cannot find order with tracking_id" + command.getTrackingId())
                );

        // Business Logic
        String message = "Rating order success!";
        OrderRating orderRating = mapper.ratingToOrderRating(command.getRating());

        order.validateAndInitializeRating(orderRating);

        Order orderSaved = repository.save(order);
        if(orderSaved == null)
            throw new OrderDomainException("Order rating can not be updated");

        // Return Response DTO
        return mapper.toRateOrderResponse(orderSaved, "Rating successfully");
    }
}

