package com.swa.order_service.order_domain.order_domain_core.entity;

import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import com.swa.order_service.order_domain.order_domain_core.valueobject.DeliveryAddress;
import com.swa.order_service.order_domain.order_domain_core.valueobject.Money;
import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Order {
    // Immutable fields - set once in the constructor
    private final UUID customerId;
    private final UUID restaurantId;
    private final DeliveryAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;
    // Mutable fields - can be changed via business logic
    private UUID orderId;
    private UUID trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;
    private ZonedDateTime createdAt;

    /**
     * BUSINESS LOGIC: Initialize Order
     */
    private void initializeOrder() {
        orderId = UUID.randomUUID();
        trackingId = UUID.randomUUID();
        orderStatus = OrderStatus.PENDING;
        createdAt = ZonedDateTime.now();
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        long id = 1;
        for(OrderItem orderItem : items)
            orderItem.initializeOrderItem(id++, orderId);
    }

    /**
     * BUSINESS RULE: Total price must greater than zero and equal the sum of items
     */
    private void validateTotalPrice() throws OrderDomainException {
        // Validate the total price: must greater than zero
        if(price == null || !price.isGreaterThanZero())
            throw new OrderDomainException("Total price must greater than zero");

        // Calculate total from items
        Money orderItemsTotal = Money.ZERO;
        for(OrderItem orderItem : items) {
            // validate order item, make sure the information of each item
            // (include quantity, price of each product and subtotal) is valid
            validateOrderItem(orderItem);
//            orderItemsTotal = orderItemsTotal.add(orderItem.getSubTotal());
            orderItemsTotal = orderItemsTotal.add(orderItem.getSubTotal());
        }

        // Compare with order price
        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException(
                    "Total price: " + price.getAmount() +
                            " is not equal to Order items total: " +
                            orderItemsTotal.getAmount() + "!");
        }
    }

    private void validateOrderItem(OrderItem orderItem) throws OrderDomainException{
        // quantity is valid
        if(!orderItem.isQuantityValid())
            throw new OrderDomainException("Quantity is invalid");
        // price is valid
        if(!orderItem.isPriceValid())
            throw new OrderDomainException("Product's price is invalid");
        // subTotal is valid
        if(!orderItem.isSubTotalValid())
            throw new OrderDomainException("Subtotal is invalid");
    }

    public void initializeAndValidateOrder() throws OrderDomainException{
        initializeOrder();
        validateTotalPrice();
    }
    /**
     * BUSINESS LOGIC: Mark order as PAID
     */
//    public void pay() {
//        if (orderStatus != OrderStatus.PENDING) {
//            throw new OrderDomainException(
//                    "Order is not in correct state for pay operation!");
//        }
//        orderStatus = OrderStatus.PAID;
//    }


}