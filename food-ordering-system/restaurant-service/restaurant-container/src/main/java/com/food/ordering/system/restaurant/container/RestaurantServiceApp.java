package com.food.ordering.system.restaurant.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.food.ordering.system.restaurant.dataaccess", "com.food.ordering.system.dataaccess" })
@EntityScan(basePackages = { "com.food.ordering.system.restaurant.dataaccess", "com.food.ordering.system.dataaccess" })
@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class RestaurantServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApp.class, args);
    }
}
