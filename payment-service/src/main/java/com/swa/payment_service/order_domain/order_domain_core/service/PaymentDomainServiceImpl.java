package com.swa.payment_service.order_domain.order_domain_core.service;

import com.swa.payment_service.order_domain.order_domain_core.entity.CreditEntry;
import com.swa.payment_service.order_domain.order_domain_core.entity.CreditHistory;
import com.swa.payment_service.order_domain.order_domain_core.entity.Payment;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentCancelledEvent;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentCompletedEvent;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.swa.payment_service.order_domain.order_domain_core.event.PaymentFailedEvent;
import com.swa.payment_service.order_domain.order_domain_core.valueobject.Money;
import com.swa.payment_service.order_domain.order_domain_core.valueobject.PaymentStatus;
import com.swa.payment_service.order_domain.order_domain_core.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j; //Annotation -> tạo logger nhanh (thông tin, cảnh báo,...)

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService{

    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT); // Khi trừ tiền thành công -> ghi nhận lịch sử giao dịch (DEBIT - trừ tiền)
        validateCreditHistory(creditEntry, creditHistories, failureMessages);
        if(failureMessages.isEmpty()){
            log.info("Payment is initiated for order id: {}", payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")));
        }else{
            log.info("Payment is failed for order id: {}", payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
        }
    }

    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        addCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);
        if(failureMessages.isEmpty()){
            log.info("Payment is cancelled for order id: {}", payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
            return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")));
        }else{
            log.info("Payment cancelled is failed for order id: {}", payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
        }
    }

    //    Kiểm tra error (payment.price > tổng tiền totalCreditAmount)
    private void validateCreditEntry(Payment payment, CreditEntry creditEntry, List<String> failureMessages) {
        if(payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())){
            log.error("Customer with id: {} doesn't have enough money in credit for payment",
                payment.getCustomerId());
            failureMessages.add("Customer with id= " + payment.getCustomerId() + "doesn't have enough money in credit for payment");
        }
    }
//    Thực hiện trừ tiền creditEntry = creditEntry - price
    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }

    private void updateCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        creditHistories.add(CreditHistory.builder()
                .creditHistoryId(UUID.randomUUID())
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build());
    }
//validate cho giao dịch vs số dư hiện tại (tổng Debit không được > tổng Credit)
    private void validateCreditHistory(CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);

        Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);

//      Error: Tổng Debit(trừ) > tổng số dư hiện tại
        if(totalDebitHistory.isGreaterThan(totalCreditHistory)){
            log.error("Customer with id: {} doesn't have enough credit according to credit history",
                    creditEntry.getCustomerId());
            failureMessages.add("Customer with id= " + creditEntry.getCustomerId() + "doesn't have enough credit according to credit history");
        }

//       Error: Số dư hiện tại != (tổng nạp(credit) - tổng trừ(debit))
        if (!creditEntry.getTotalCreditAmount().equals(totalCreditHistory.subtract(totalDebitHistory))) {
            log.error("Credit history total is not equal to current credit for customer id: {}",
                    creditEntry.getCustomerId());
            failureMessages.add("Credit history total is not equal to current credit for customer id: " +
                    creditEntry.getCustomerId());
        }


    }
//sum(creditHistories) where transactionType=??
    private static Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream() // Thay vì for -> dùng stream
                .filter(creditHistory -> transactionType == creditHistory.getTransactionType())  // where
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add); // bắt đầu = 0 -> lần lượt + -> return Money
    }
//  CỘng lại tiền khi cancel giao dịch
    private void addCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAmount(payment.getPrice());
    }
}
