package com.food.ordering.system.payment.service.domain.entity;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.payment.service.domain.valueobject.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class CreditHistory {
    private UUID creditHistoryId;
    private final UUID customerId;
    private final Money amount;
    private final TransactionType transactionType;
}
