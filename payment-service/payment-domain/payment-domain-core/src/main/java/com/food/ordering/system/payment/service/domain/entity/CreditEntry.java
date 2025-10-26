package com.food.ordering.system.payment.service.domain.entity;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class CreditEntry {
    private UUID creditEntryId;
    private final UUID customerId;
    private Money totalCreditAmount;

    public void addCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }
}
