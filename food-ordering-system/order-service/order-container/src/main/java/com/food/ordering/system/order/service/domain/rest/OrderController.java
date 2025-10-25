package com.food.ordering.system.order.service.domain.rest;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.food.ordering.system.order.service.domain.ports.input.OrderApplicationService;

@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
@Slf4j

public class OrderController {
    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @Valid @RequestBody CreateOrderCommand createOrderCommand) {
// Log request
        log.info("Creating order for customer: {} at restaurant: {}",
                createOrderCommand.getCustomerId(),
                createOrderCommand.getRestaurantId());
// Delegate to use case (Input Port)
        CreateOrderResponse response =
                orderApplicationService.createOrder(createOrderCommand);
// Log success
        log.info("Order created with tracking id: {}",
                response.getOrderTrackingId());
// Return HTTP 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
