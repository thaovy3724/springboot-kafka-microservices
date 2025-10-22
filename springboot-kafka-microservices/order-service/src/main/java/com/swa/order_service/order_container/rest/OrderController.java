package com.swa.order_service.order_container.rest;

import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.create.CreateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.history.HistoryOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderCommand;
import com.swa.order_service.order_domain.order_application_service.dto.rating.RateOrderResponse;
import com.swa.order_service.order_domain.order_application_service.dto.statistics.StatisticsResponse;
import com.swa.order_service.order_domain.order_application_service.dto.update.add.UpdateAddItemsCommand;
import com.swa.order_service.order_domain.order_application_service.dto.update.add.UpdateAddItemsResponse;
import com.swa.order_service.order_domain.order_application_service.ports.input.OrderApplicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
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

    @PutMapping("/update/add")
    public ResponseEntity<UpdateAddItemsResponse> updateAddItem(
            @Valid @RequestBody UpdateAddItemsCommand updateAddOrderItemsCommand
    ) {
        log.info("Update - adding items for order with ID: {}",
                updateAddOrderItemsCommand.getTrackingId()
        );
        UpdateAddItemsResponse response =
                orderApplicationService.updateAddItems(
                        updateAddOrderItemsCommand
                );
        log.info("Update add order items result: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HistoryOrderResponse>> getOrderByCustomerId(
            @NotNull @RequestParam("customerId") UUID customerId){
        // Log request
        log.info("Get history order of customer_id: {}",
                customerId);
        // Delegate to use case (Input Port)
        List<HistoryOrderResponse> response =
                orderApplicationService.getOrderByCustomerId(customerId);
        // Log success
        log.info("History order: {}", response);
        // Return HTTP 201 Created
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getStatistics(){
        // Delegate to use case (Input Port)
        StatisticsResponse response =
                orderApplicationService.getStatistics();
        // Log success
        log.info("Statistic: {}", response);
        // Return HTTP 201 Created
        return ResponseEntity.ok(response);
    }

    @PutMapping("/rating")
    public ResponseEntity<RateOrderResponse> rateOrder(
            @Valid @RequestBody RateOrderCommand ratingCommand){
        // Log request
        log.info("rating order with tracking_id: {}", ratingCommand.getTrackingId());
        // Delegate to use case (Input Port)
        RateOrderResponse response =
                orderApplicationService.rateOrder(ratingCommand);
        // Log success
        log.info("Order is rated: {}", response);
        // Return HTTP 201 Created
        return ResponseEntity.ok(response);
    }
}