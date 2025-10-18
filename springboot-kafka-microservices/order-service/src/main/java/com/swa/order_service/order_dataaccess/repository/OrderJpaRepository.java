package com.swa.order_service.order_dataaccess.repository;

import com.swa.order_service.order_dataaccess.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

}
