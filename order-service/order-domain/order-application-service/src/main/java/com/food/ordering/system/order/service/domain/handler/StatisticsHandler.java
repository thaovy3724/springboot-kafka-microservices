package com.food.ordering.system.order.service.domain.handler;

import com.food.ordering.system.order.service.domain.dto.statistics.StatisticsResponse;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.OrderRepository;
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

