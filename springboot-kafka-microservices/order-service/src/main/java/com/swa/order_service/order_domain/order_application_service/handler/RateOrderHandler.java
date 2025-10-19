package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.mapper.OrderDataMapper;
import com.swa.order_service.order_domain.order_application_service.ports.output.OrderRepository;
import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import com.swa.order_service.order_domain.order_domain_core.exception.OrderNotFoundException;
import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderRating;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class RateOrderHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public RateOrderHandler(OrderDataMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public RateOrderResponse rateOrder(RateOrderCommand command){
        // find order by tracking_id
        Order order = repository.findByTrackingId(command.getTrackingId())
                .orElseThrow(()->
                        new OrderNotFoundException("Cannot find order with tracking_id" + command.getTrackingId())
                );

        // Business Logic
        String message = "Rating order success!";

        order.validateRating();

        OrderRating orderRating = mapper.ratingToOrderRating(command.getRating());
        int rowCount = repository.updateRatingByTrackingId(command.getTrackingId(), orderRating);
        if(rowCount != 1)
            throw new OrderDomainException("Order rating can not be updated");

        // return Response DTO
        return mapper.toRateOrderResponse(command.getTrackingId(), command.getRating(), message);

    }
}
