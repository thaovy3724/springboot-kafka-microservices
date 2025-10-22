package com.swa.order_service.order_domain.order_domain_core.entity;

import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import com.swa.order_service.order_domain.order_domain_core.valueobject.DeliveryAddress;
import com.swa.order_service.order_domain.order_domain_core.valueobject.Money;
import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderStatus;
import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderRating;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class Order {
    // Immutable fields - set once in the constructor
    private final UUID customerId;
    private final UUID restaurantId;
    private final DeliveryAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;
    // Mutable fields - can be changed via business logic
    private OrderRating rating;
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

    private void validateOrder(){
        validateTotalPrice();
        deliveryAddress.validateDeliveryAddress();
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
        validateOrder();
    }

    private void validateRating(OrderRating orderRating) throws OrderDomainException{
        // check the order status: if the order status is APPROVED, allow to rate order
        if(orderStatus != OrderStatus.APPROVED)
            throw new OrderDomainException("Order is not in correct state for rate");
        // validate the rating
        if(!orderRating.validateRating())
            throw new OrderDomainException("You can only rate from 1 to 5 star");
    }

    public void validateAndInitializeRating(OrderRating orderRating){
        validateRating(orderRating);
        rating = orderRating;
    }

    public void checkIfOrderStatusIsPending() {
        if (this.orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order status is not valid (must be PENDING)");
        }
    }

    public Order validateAndUpdateAddItems(List<OrderItem> items) {
        // Check order status
        checkIfOrderStatusIsPending();
        for (OrderItem item: items) {
            validateOrderItem(item);
        }
        Order updatedAddOrder = addItemsAndCalculatePrice(items);
        validateTotalPrice();
        //
        return updatedAddOrder;
    }

    public Order addItemsAndCalculatePrice(List<OrderItem> newItems) {
        // Tạo list item updated để update bằng cách copy list item của order
        List<OrderItem> updated = new ArrayList<>(this.items);

        // Với mỗi item trong list truyền vào
        for (OrderItem item : newItems) {
            // Đặt cờ hiệu
            boolean found = false;
            // Duyệt qua từng phần tử trong list updated
            for (int i = 0; i < updated.size(); i++) {
                // Lấy ra phần tử hiện tại của list updated trong vòng lặp gán vào biến existing
                OrderItem existing = updated.get(i);
                // Nếu id của existing trùng với id của item ở vòng for ngoài (item trong newItems)
                if (existing.getProductId().equals(item.getProductId())) {
                    // => đã tìm thấy có tồn tại,
                    // tăng số lượng của item lên bật cờ hiệu và break ra khỏi vòng lặp
                    updated.set(i, existing.increaseQuantity(item.getQuantity()));
                    //, đồng thời tăng thêm price bằng subtotal,

                    found = true;
                    break;
                }
            }
            // Nếu cờ hiệu không được bật (tức item hiện tại trong newItems không trùng với items nào trong updated)
            if (!found) {
                updated.add(item);
            }
        }

        // Tính lại tổng tiền sau khi có được danh sách các items sau cập nhật
        Money updatedPrice = calculateTotalPrice(updated);

        // Trả về chính bản thân object Order với list items và total price sau khi cập nhật
        return this.toBuilder()
                .items(updated)
                .price(updatedPrice)
                .build();
    }

    // Helper method: Tính tổng tiền của danh sách item
    private Money calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(Money.ZERO, Money::add);
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