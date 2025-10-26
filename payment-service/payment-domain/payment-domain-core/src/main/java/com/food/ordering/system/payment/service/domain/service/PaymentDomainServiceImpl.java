package com.food.ordering.system.payment.service.domain.service;


import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import com.food.ordering.system.payment.service.domain.valueobject.PaymentStatus;
import com.food.ordering.system.payment.service.domain.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService{

    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();

        validateCreditEntry(payment, creditEntry, failureMessages);
        //validateCreditHistory(creditEntry, creditHistories, failureMessages);

        // Nếu không xảy ra lỗi nào
        if(failureMessages.isEmpty()) {
            log.info("Payment is initiated for order id {}", payment.getOrderId());
            // Trừ tiền
            subtractCreditEntry(payment, creditEntry);
            // Cập nhật lịch sử giao dịch (trừ tiền)
            addCreditHistory(payment, creditHistories, TransactionType.DEBIT);
            // Cập nhật trạng thái Payment
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            // Trả về PaymentCompletedEvent
            return new PaymentCompletedEvent(payment,
                    ZonedDateTime.now(ZoneId.of("UTC")));
        } else { // Trả về FAILED nếu không hợp lệ
            log.info("Payment initiation is failed for order id {}",
                    payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment,
                    ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
        }
    }

    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        payment.validatePayment(failureMessages);

        // Cộng lại số tiền đã trừ
        addCreditEntry(payment, creditEntry);
        // Cập nhật lại credit history (nạp tiền)
        addCreditHistory(payment, creditHistories, TransactionType.CREDIT);

        if(failureMessages.isEmpty()) {
            log.info("Payment is cancelled for order id {}", payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
            return new PaymentCancelledEvent(payment,
                    ZonedDateTime.now(ZoneId.of("UTC")));
        } else {
            log.info("Payment cancellation is failed for order id {}",
                    payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment,
                    ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
        }
    }

    // Validate: Kiểm tra xem số tiền cần thanh toán có > số dư hiện tại hay
    // không?
    private void validateCreditEntry(Payment payment, CreditEntry creditEntry, List<String> failureMessages) {
        if(payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Customer with id: {} doesn't have enough money in " +
                    "credit for payment", payment.getCustomerId());

            failureMessages.add("Customer with id: " + payment.getCustomerId() + "doesn't have enough money in credit for payment");
        }
    }

    // Trừ tiền trong số dư hiện tại
    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }

    // Cập nhật lịch sử credit
    private void addCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        creditHistories.add(CreditHistory.builder()
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build());
    }

    private static Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }

    //private void validateCreditHistory(CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
    //    Money totalCreditHistory = getTotalHistoryAmount(creditHistories,
    //            TransactionType.CREDIT);
    //
    //    Money totalDebitHistory = getTotalHistoryAmount(creditHistories,
    //            TransactionType.DEBIT);
    //
    //    if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
    //        log.error("Customer with id: {} doesn't have enough credit " +
    //                "according to credit history", creditEntry.getCustomerId());
    //        failureMessages.add("Customer with id " + creditEntry.getCustomerId() + " doesn't have enough credit according to credit history");
    //    }
    //
    //    // Nếu số dư hiện tại không = tổng Credit - tổng Debit
    //    if(!creditEntry.getTotalCreditAmount().equals(totalCreditHistory.subtract(totalDebitHistory))) {
    //        log.error("Credit history total is not equal to current credit for customer id: {}", creditEntry.getCustomerId());
    //        failureMessages.add("Credit history total is not equal to current credit for customer id " + creditEntry.getCustomerId());
    //    }
    //}

    private void addCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAmount(payment.getPrice());
    }
}
