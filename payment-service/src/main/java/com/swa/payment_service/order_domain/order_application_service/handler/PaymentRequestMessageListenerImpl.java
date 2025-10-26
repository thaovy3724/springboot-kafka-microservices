package com.swa.payment_service.order_domain.order_application_service.handler;

import com.swa.payment_service.order_domain.order_application_service.dto.PaymentRequest;
import com.swa.payment_service.order_domain.order_application_service.ports.input.PaymentRequestMessageListener;
import com.swa.payment_service.order_domain.order_application_service.ports.ouput.PaymentCancelledMessagePublisher;
import com.swa.payment_service.order_domain.order_application_service.ports.ouput.PaymentCompleteMessagePublisher;
import com.swa.payment_service.order_domain.order_application_service.ports.ouput.PaymentFailedMessagePublisher;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentCancelledEvent;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentCompletedEvent;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentEvent;
import com.swa.payment_service.order_domain.order_domain_core.event.PaymentFailedEvent;
import org.springframework.stereotype.Service; // Spring bean -> Tầng BUS
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//Tiếp nhận MS Request từ các service khác -> Chuyển yêu cầu qua Domain xử lý nghiệp vụ
//Order Service -> Input port(payment) -> này (gọi PaymentDomainService) -> PaymentResponsitory
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;
    private final PaymentCompleteMessagePublisher paymentCompleteMessagePublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
    private final PaymentFailedMessagePublisher  paymentFailedMessagePublisher;

    public PaymentRequestMessageListenerImpl(PaymentRequestHelper paymentRequestHelper, PaymentCompleteMessagePublisher paymentCompleteMessagePublisher, PaymentCancelledMessagePublisher paymentCancelledMessagePublisher, PaymentFailedMessagePublisher paymentFailedMessagePublisher) {
        this.paymentRequestHelper = paymentRequestHelper;
//        Phát event ra Kafka
        this.paymentCompleteMessagePublisher = paymentCompleteMessagePublisher;
        this.paymentCancelledMessagePublisher = paymentCancelledMessagePublisher;
        this.paymentFailedMessagePublisher = paymentFailedMessagePublisher;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest);
        fireEvent(paymentEvent); //publish ra event tương ứng
    }
//  Xác định loại event domain trả về
//    Gọi publish tương ứng để publish ra Kafka
    private void fireEvent(PaymentEvent paymentEvent) {
        log.info("Publishing payment event with payment id: {} and order id: {}",
                paymentEvent.getPayment().getPaymentId(), paymentEvent.getPayment().getOrderId());
        if(paymentEvent instanceof PaymentCompletedEvent){
            paymentCompleteMessagePublisher.publish((PaymentCompletedEvent) paymentEvent);
        }else if(paymentEvent instanceof PaymentCancelledEvent){
            paymentCancelledMessagePublisher.publish((PaymentCancelledEvent) paymentEvent);
        }else if(paymentEvent instanceof PaymentFailedEvent){
            paymentFailedMessagePublisher.publish((PaymentFailedEvent) paymentEvent);
        }
    }
}
