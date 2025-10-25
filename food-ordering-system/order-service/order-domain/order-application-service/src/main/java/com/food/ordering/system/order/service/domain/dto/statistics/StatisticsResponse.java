package com.food.ordering.system.order.service.domain.dto.statistics;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class StatisticsResponse {
    private final long totalOrders;
    private final BigDecimal totalRevenue;
    private final BigDecimal avgOrderValue;
}
