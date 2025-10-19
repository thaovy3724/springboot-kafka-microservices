package com.swa.order_service.order_domain.order_domain_core.valueobject;

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
