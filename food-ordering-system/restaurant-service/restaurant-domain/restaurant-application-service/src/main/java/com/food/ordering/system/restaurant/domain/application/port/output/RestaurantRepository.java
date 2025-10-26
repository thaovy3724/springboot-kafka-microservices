package com.food.ordering.system.restaurant.domain.application.port.output;

import com.food.ordering.system.restaurant.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
