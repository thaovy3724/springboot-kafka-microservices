package com.swa.order_service.order_domain.order_domain_core.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero(){
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public Money add(Money money){
        return new Money(amount.add(money.getAmount()));
    }

    public Money multiply(int quantity){
        return new Money(amount.multiply(new BigDecimal(quantity)));
    }

    public BigDecimal getAmount(){
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

}

