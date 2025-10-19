package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.statistics.StatisticsResponse;
import com.swa.order_service.order_domain.order_application_service.mapper.OrderDataMapper;
import com.swa.order_service.order_domain.order_application_service.ports.output.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StatisticsHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public StatisticsHandler(OrderDataMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public StatisticsResponse getStatistics(){
        // get total orders
        long totalOrders = repository.getTotalOrders();
        // get total revenue
        BigDecimal totalRevenue = repository.calculateTotalRevenue();
        // get average order value
        BigDecimal avgOrderValue = repository.calculateAvgOrderValue();

        return StatisticsResponse.builder()
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .avgOrderValue(avgOrderValue)
                .build();
    }
}
