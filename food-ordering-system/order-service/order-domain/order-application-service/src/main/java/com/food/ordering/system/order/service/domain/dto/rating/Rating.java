package com.food.ordering.system.order.service.domain.dto.rating;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
public class Rating {
    @NotNull
    public final int star;
    public final String comment;
}
