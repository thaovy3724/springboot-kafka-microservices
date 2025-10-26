package com.swa.payment_service.order_domain.order_domain_core.service;

import com.swa.payment_service.order_domain.order_domain_core.entity.CreditEntry;
import com.swa.payment_service.order_domain.order_domain_core.entity.CreditHistory;
import com.swa.payment_service.order_domain.order_domain_core.entity.Payment;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentEvent;

import java.util.List;
// Chứa logic nghiệp vụ (ktra, xử lý - BUS)
public interface PaymentDomainService{
//    Dùng khi bắt đầu thanh toán -> transactionType: DEBIT
    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);
//    Dùng khi hủy thanh toán -> transactionType: CREDIT
    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages);
}