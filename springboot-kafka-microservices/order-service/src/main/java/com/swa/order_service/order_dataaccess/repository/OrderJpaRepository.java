package com.swa.order_service.order_dataaccess.repository;

import com.swa.order_service.order_dataaccess.entity.OrderEntity;
import com.swa.order_service.order_dataaccess.entity.OrderRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByCustomerId(UUID customerId);
    Optional<OrderEntity> findByTrackingId(UUID trackingId);

    @Query("SELECT COUNT(*) FROM OrderEntity")
    long getTotalOrders();

    @Query("SELECT SUM(price) FROM OrderEntity")
    BigDecimal calculateTotalRevenue();

    @Query("SELECT AVG(price) FROM OrderEntity")
    BigDecimal calculateAvgOrderValue();

    @Modifying
    @Query("UPDATE OrderEntity SET rating = :rating WHERE trackingId = :trackingId")
    int updateRatingByTrackingId(@Param("trackingId") UUID trackingId,
                                 @Param("rating") OrderRatingEntity orderRatingEntity);
}
