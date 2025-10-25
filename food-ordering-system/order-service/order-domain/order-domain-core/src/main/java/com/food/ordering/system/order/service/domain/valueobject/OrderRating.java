package com.food.ordering.system.order.service.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRating {
    public final int star;
    public final String comment;

    public boolean validateRating(){
        // The star must be from 1 - 5
        return star >= 1 && star <=5;
    }
}
