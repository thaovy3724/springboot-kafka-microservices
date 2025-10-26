package com.food.ordering.system.payment.service.domain.handler;

import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.exception.PaymentApplicationServiceException;
import com.food.ordering.system.payment.service.domain.mapper.PaymentDataMapper;
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.food.ordering.system.payment.service.domain.ports.output.repository.PaymentRepository;
import com.food.ordering.system.payment.service.domain.service.PaymentDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
// Nhận PaymentRequest từ input port (impl) -> Map từ PaymentRequest (DTO)
// sang Payment (enitity) -> Gọi PaymentDomainService để xử lý logic -> Lưu
// kết quả qua repository -> fire event qua output port (publisher)
public class PaymentRequestHelper {
    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper mapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;

    public PaymentRequestHelper(PaymentDomainService paymentDomainService,
                                PaymentDataMapper mapper,
                                PaymentRepository paymentRepository,
                                CreditEntryRepository creditEntryRepository,
                                CreditHistoryRepository creditHistoryRepository) {
        this.paymentDomainService = paymentDomainService;
        this.mapper = mapper;
        this.paymentRepository = paymentRepository;
        this.creditEntryRepository = creditEntryRepository;
        this.creditHistoryRepository = creditHistoryRepository;
    }

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest){
        log.info("Received payment complete event for orderId: {}", paymentRequest.getOrderId());
        Payment payment = mapper.paymentRequestToPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);
        persistDbObjects(payment, failureMessages, creditEntry, creditHistories);
        return paymentEvent;
    }

    @Transactional
    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest) {
        log.info("Receive payment rollback event for order id: {}",
                paymentRequest.getOrderId());

        Optional<Payment> paymentResponse =
                paymentRepository.findByOrderId(UUID.fromString(paymentRequest.getOrderId()));
        // Không tìm thấy payment có order id phù hợp
        if(paymentResponse.isEmpty()) {
            log.error("Payment with order id {} could not be found!",
                    paymentRequest.getOrderId());
            throw new PaymentApplicationServiceException("Payment with order " +
                    "id " + paymentRequest.getOrderId() + "could not be found" +
                    ".");
        }

        Payment payment = paymentResponse.get();
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories =
                getCreditHistory(payment.getCustomerId());
        List<String> failureMessage = new ArrayList<>();

        PaymentEvent paymentEvent =
                paymentDomainService.validateAndCancelPayment(payment,
                        creditEntry, creditHistories, failureMessage);
        persistDbObjects(payment, failureMessage, creditEntry, creditHistories);
        return paymentEvent;
    }

    private void persistDbObjects(Payment payment, List<String> failureMessages, CreditEntry creditEntry, List<CreditHistory> creditHistories) {
        paymentRepository.save(payment);
        if(failureMessages.isEmpty()){
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size()-1));
        }
    }

    private List<CreditHistory> getCreditHistory(UUID customerId) {
        Optional<List<CreditHistory>> creditHistories =
                creditHistoryRepository.findByCustomerId(customerId);
        if (creditHistories.isEmpty()) {
            log.error("Could not find credit history for customer: {}", customerId);
            // Vì customerId là UUID nên không có .getValue()
            throw new PaymentApplicationServiceException("Could not find credit history for customer: " +
                    customerId);
        }
        return creditHistories.get();
    }

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
