package com.swa.order_service;

import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.entity.OrderItem;
import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import com.swa.order_service.order_domain.order_domain_core.valueobject.DeliveryAddress;
import com.swa.order_service.order_domain.order_domain_core.valueobject.Money;
import com.swa.order_service.order_domain.order_domain_core.valueobject.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Order Initialize and Validate Test")
public class OrderTest {
    @Mock
    OrderItem orderItem1;

    @Mock
    OrderItem orderItem2;

    private DeliveryAddress deliveryAddress;
    private List<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        deliveryAddress = DeliveryAddress.builder()
                .street("273 An Dương Vương")
                .postalCode("170898")
                .city("Thành phố Hồ Chí Minh")
                .build();

        orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
    }

    /* Test case 1: Khởi tạo và kiểm tra đơn hàng thành công (đơn hàng hợp
    lệ) */
    @Test
    @DisplayName("Initialize and Validate order successfully")
    void initializeAndValidateOrderTest_Success() {
        // Arrage
        Money itemPrice1 = new Money(new BigDecimal("10.00"));
        Money itemPrice2 = new Money(new BigDecimal("15.00"));
        Money totalPrice = new Money(new BigDecimal("25.00"));
        
        when(orderItem1.getSubTotal()).thenReturn(itemPrice1);
        when(orderItem2.getSubTotal()).thenReturn(itemPrice2);
        
        when(orderItem1.isQuantityValid()).thenReturn(true);
        when(orderItem1.isPriceValid()).thenReturn(true);
        when(orderItem1.isSubTotalValid()).thenReturn(true);
        
        when(orderItem2.isQuantityValid()).thenReturn(true);
        when(orderItem2.isPriceValid()).thenReturn(true);
        when(orderItem2.isSubTotalValid()).thenReturn(true);

        Order order = Order.builder()
                .customerId(UUID.randomUUID())
                .restaurantId(UUID.randomUUID())
                .deliveryAddress(deliveryAddress)
                .price(new Money(new BigDecimal("25.00")))
                .items(orderItems)
                .build();
        
        // Act
        order.initializeAndValidateOrder();
        
        // Assert
        assertNotNull(order.getOrderId(), "Order Id is null!");
        assertNotNull(order.getTrackingId(), "Tracking Id is null!");
        assertNotNull(order.getCreatedAt(), "Create at is null!");

        assertEquals(OrderStatus.PENDING, order.getOrderStatus(),
                "Order status should be PENDING");

        // Verify
        verify(orderItem1, times(1)).initializeOrderItem(eq(1L),
                any(UUID.class));
        verify(orderItem2, times(1)).initializeOrderItem(eq(2L),
                any(UUID.class));

        verify(orderItem1, times(1)).isQuantityValid();
        verify(orderItem1, times(1)).isPriceValid();
        verify(orderItem1, times(1)).isSubTotalValid();

        verify(orderItem2, times(1)).isQuantityValid();
        verify(orderItem2, times(1)).isPriceValid();
        verify(orderItem2, times(1)).isSubTotalValid();
    }

    /* Test case 2: Ném ngoại lệ khi total price null */
    @Test
    @DisplayName("Throw exception when total price is null")
    void initializeAndValidateOrderTest_NullTotalPrice() {
        // Arrage
        Order order = Order.builder()
                .customerId(UUID.randomUUID())
                .restaurantId(UUID.randomUUID())
                .deliveryAddress(deliveryAddress)
                .price(null)
                .items(orderItems)
                .build();

        // Act & Assert
        OrderDomainException exception = assertThrows(
          OrderDomainException.class,
                () -> order.initializeAndValidateOrder(),
                "Should throw OrderDomainException when total price is null"
        );

        assertEquals("Total price must greater than zero",
                exception.getMessage());

        // Verify - Kiểm tra các phương thức validate order item không được
        // gọi lần nào
        verify(orderItem1, never()).isQuantityValid();
        verify(orderItem1, never()).isPriceValid();
        verify(orderItem1, never()).isSubTotalValid();
        verify(orderItem1, never()).getSubTotal();

        verify(orderItem2, never()).isQuantityValid();
        verify(orderItem2, never()).isPriceValid();
        verify(orderItem2, never()).isSubTotalValid();
        verify(orderItem2, never()).getSubTotal();

    }

    /* Test case 3: Ném ngoại lệ khi total price bằng 0 */
    @Test
    @DisplayName("Throw exception when total price is zero")
    void inititalizeAndValidateOrderTest_ZeroPrice() {
        // Arrage
        Order order = Order.builder()
                .customerId(UUID.randomUUID())
                .restaurantId(UUID.randomUUID())
                .deliveryAddress(deliveryAddress)
                .price(new Money(new BigDecimal(0)))
                .items(orderItems)
                .build();

        // Act & Assert
        OrderDomainException exception = assertThrows(
                OrderDomainException.class,
                () -> order.initializeAndValidateOrder(),
                "Should throw OrderDomainException when total price is zero"
        );

        assertEquals("Total price must greater than zero",
                exception.getMessage());

        // Verify
        verify(orderItem1, never()).isQuantityValid();
        verify(orderItem1, never()).isPriceValid();
        verify(orderItem1, never()).isSubTotalValid();
        verify(orderItem1, never()).getSubTotal();

        verify(orderItem2, never()).isQuantityValid();
        verify(orderItem2, never()).isPriceValid();
        verify(orderItem2, never()).isSubTotalValid();
        verify(orderItem2, never()).getSubTotal();
    }

    /* Test case 4: Ném ngoại lệ khi total price < 0 */


    /* Test case 5: Ném ngoại lệ khi tổng sub total của order item khác total
     price */


    /* Test case 6: Ném ngoại lệ khi order item quantity không hợp lệ */


    /* Test case 7: Ném ngoại lệ khi order item price không hợp lệ */


    /* Test case 8: Ném ngoại lệ khi order item sub total không hợp lệ */
}
