package com.food.ordering.system.payment.service.dataaccess.mapper;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.payment.service.dataaccess.entity.CreditEntryEntity;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataAccessMapper {
    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntryEntity) {
        return CreditEntry.builder()
                .creditEntryId(creditEntryEntity.getId())
                .customerId(creditEntryEntity.getCustomerId())
                .totalCreditAmount(new Money(creditEntryEntity.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getCreditEntryId())
                .customerId(creditEntry.getCustomerId())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }
}
