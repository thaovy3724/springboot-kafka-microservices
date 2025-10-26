package com.swa.payment_service.order_domain.order_application_service.handler;

import com.swa.payment_service.order_domain.order_application_service.dto.PaymentRequest;
import com.swa.payment_service.order_domain.order_application_service.exception.PaymentApplicationServiceException;
import com.swa.payment_service.order_domain.order_application_service.mapper.PaymentDataMapper;
import com.swa.payment_service.order_domain.order_application_service.ports.ouput.CreditEntryRepository;
import com.swa.payment_service.order_domain.order_application_service.ports.ouput.CreditHistoryRepository;
import com.swa.payment_service.order_domain.order_application_service.ports.ouput.PaymentRepository;
import com.swa.payment_service.order_domain.order_domain_core.entity.CreditEntry;
import com.swa.payment_service.order_domain.order_domain_core.entity.CreditHistory;
import com.swa.payment_service.order_domain.order_domain_core.entity.Payment;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentEvent;
import com.swa.payment_service.order_domain.order_domain_core.service.PaymentDomainService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
//DataMapper, DomainService, Repository
public class PaymentRequestHelper {
    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;


    public PaymentRequestHelper(PaymentDomainService paymentDomainService, PaymentDataMapper paymentDataMapper, PaymentRepository paymentRepository, CreditEntryRepository creditEntryRepository, CreditHistoryRepository creditHistoryRepository) {
        this.paymentDomainService = paymentDomainService;
        this.paymentDataMapper = paymentDataMapper;
        this.paymentRepository = paymentRepository;
        this.creditEntryRepository = creditEntryRepository;
        this.creditHistoryRepository = creditHistoryRepository;
    }

// đảm bảo save – nếu lỗi, rollback tất cả để dữ liệu không bị sai lệch.
//  Ghi xuống DB - Thanh toán đơn hàng
    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest){
        log.info("Received payment complete event for orderId: {}", paymentRequest.getOrderId());
        Payment payment = paymentDataMapper.paymentRequestToPayment(paymentRequest); // DTO nhận từ kafka -> Entity Payment
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>(); // Gom lỗi phát sinh
        PaymentEvent paymentEvent = paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages); //Gọi Service thanh toán, sinh ra Payment Kafka
        persistDbObject(payment, failureMessages, creditEntry, creditHistories);
        return paymentEvent;

    }
// Lưu DB
    private void persistDbObject(Payment payment, List<String> failureMessages, CreditEntry creditEntry, List<CreditHistory> creditHistories) {
        paymentRepository.save(payment); // Lưu DB thông tin đơn hàng
        if(failureMessages.isEmpty()){
            creditEntryRepository.save(creditEntry); // -> Debit
            creditHistoryRepository.save(creditHistories.get(creditHistories.size()-1)); // -> Lưu cuối bảng
        }
    }

    //    Cancel
    @Transactional
    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest){
        log.info("Received payment rollback event for orderId: {}", paymentRequest.getOrderId());
        Optional<Payment> paymentResponse = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.getOrderId())); // Tìm Payment theo OrderId
        if(paymentResponse.isEmpty()){
            log.error("Payment with order id: {} could not be found", paymentRequest.getOrderId());
            throw new PaymentApplicationServiceException("Payment with order id: " + paymentRequest.getOrderId());
        }
        Payment payment = paymentResponse.get();
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());

        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndCancelPayment(payment, creditEntry, creditHistories, failureMessages);
        persistDbObject(payment, failureMessages, creditEntry, creditHistories);
        return paymentEvent;
    }

//    Lấy lịch sử giao dịch của IdCustomer
    private List<CreditHistory> getCreditHistory(UUID customerId) {
        Optional<List<CreditHistory>> creditHistories = creditHistoryRepository.findByCustomerID(customerId);
        if (creditHistories.isEmpty()) {
            log.error("Could not find credit history for customer: {}", customerId);
            throw new PaymentApplicationServiceException("Could not find credit history for customer: " +
                    customerId);
        }
        return creditHistories.get();
    }
// Lấy thông tin số dư từ DB theo ID customer
    private CreditEntry getCreditEntry(UUID customerId) {
        Optional<CreditEntry> creditEntry = creditEntryRepository.findByCustomerId(customerId);
        if (creditEntry.isEmpty()) {
            log.error("Could not find credit entry for customer: {}", customerId);
            throw new PaymentApplicationServiceException("Could not find credit entry for customer: " +
                    customerId);
        }
        return creditEntry.get();
    }
}
