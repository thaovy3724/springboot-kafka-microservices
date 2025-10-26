package com.food.ordering.system.payment.service.domain.handler;


import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import com.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.food.ordering.system.payment.service.domain.ports.output.messager.publisher.PaymentCancelledMessagePublisher;
import com.food.ordering.system.payment.service.domain.ports.output.messager.publisher.PaymentCompletedMessagePublisher;
import com.food.ordering.system.payment.service.domain.ports.output.messager.publisher.PaymentFailedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper helper;
    private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
    private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;

    public PaymentRequestMessageListenerImpl(PaymentRequestHelper helper, PaymentCompletedMessagePublisher paymentCompletedMessagePublisher, PaymentCancelledMessagePublisher paymentCancelledMessagePublisher, PaymentFailedMessagePublisher paymentFailedMessagePublisher) {
        this.helper = helper;
        this.paymentCompletedMessagePublisher = paymentCompletedMessagePublisher;
        this.paymentCancelledMessagePublisher = paymentCancelledMessagePublisher;
        this.paymentFailedMessagePublisher = paymentFailedMessagePublisher;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = helper.persistPayment(paymentRequest);
        publishEvent(paymentEvent);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent =
                helper.persistCancelPayment(paymentRequest);
        publishEvent(paymentEvent);
    }

    public void publishEvent(PaymentEvent paymentEvent) {
        log.info("Publishing event with payment id: {} and order id: {}",
                paymentEvent.getPayment().getPaymentId(),
                paymentEvent.getPayment().getOrderId());

        if(paymentEvent instanceof PaymentCompletedEvent) {
            paymentCompletedMessagePublisher.publish((PaymentCompletedEvent) paymentEvent);
        } else if (paymentEvent instanceof PaymentCancelledEvent) {
            paymentCancelledMessagePublisher.publish((PaymentCancelledEvent) paymentEvent);
        } else if (paymentEvent instanceof PaymentFailedEvent) {
            paymentFailedMessagePublisher.publish((PaymentFailedEvent) paymentEvent);
        }
    }
}
