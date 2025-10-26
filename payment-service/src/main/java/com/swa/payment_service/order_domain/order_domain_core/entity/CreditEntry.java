package com.swa.payment_service.order_domain.order_domain_core.entity;

import com.swa.payment_service.order_domain.order_domain_core.valueobject.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreditEntry {
    private UUID creditEntryId;
    private final UUID customerId;
    private Money totalCreditAmount;

    public void addCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }
}
