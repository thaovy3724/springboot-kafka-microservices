package com.swa.payment_service.order_domain.order_domain_core.entity;

import com.swa.payment_service.order_domain.order_domain_core.valueobject.Money;
import com.swa.payment_service.order_domain.order_domain_core.valueobject.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder
public class CreditHistory {
    private UUID creditHistoryId;
    private final UUID customerId;
    private final Money amount;

    private final TransactionType transactionType;
}
