package com.food.ordering.system.payment.service.dataaccess.adapter;


import com.food.ordering.system.payment.service.dataaccess.entity.PaymentEntity;
import com.food.ordering.system.payment.service.dataaccess.mapper.PaymentDataAccessMapper;
import com.food.ordering.system.payment.service.dataaccess.repository.PaymentJpaRepository;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.ports.output.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper mapper;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository, PaymentDataAccessMapper mapper) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Payment save(Payment payment) {
        // Bước 1: Domain -> JpaEntity
        PaymentEntity paymentEntity = mapper.paymentToPaymentEntity(payment);
        // Bước 2: Save to database
        PaymentEntity savedPaymentEntity =
                paymentJpaRepository.save(paymentEntity);
        // Bước 3: JpaEntity -> Domain
        return mapper.paymentEntityToPayment(savedPaymentEntity);
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(mapper::paymentEntityToPayment);
    }
}
