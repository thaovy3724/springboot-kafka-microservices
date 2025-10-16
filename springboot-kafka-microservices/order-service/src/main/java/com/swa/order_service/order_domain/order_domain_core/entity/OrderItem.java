package com.swa.order_service.order_domain.order_domain_core.entity;

import com.swa.order_service.order_domain.order_domain_core.valueobject.Money;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class OrderItem {
    // mutual
    private long id;
    private UUID orderId;
    // immutual
    private final UUID productId;
    private final Money price;
    private final int quantity;
    private final Money subTotal;

    public void initializeOrderItem(long id, UUID orderId){
        this.id = id;
        this.orderId = orderId;
    }

    public boolean isQuantityValid(){
        return quantity > 0;
    }

    public boolean isPriceValid(){
        return price != null && price.isGreaterThanZero();
    }

    public boolean isSubTotalValid(){
        return subTotal != null && subTotal.equals(price.multiply(new Money(new BigDecimal(quantity))));
    }

}

