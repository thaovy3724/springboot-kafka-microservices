package com.food.ordering.system.payment.service.domain.handler;


import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHandler paymentRequestHandler;
    //private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
    //private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
    //private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;

    //public PaymentRequestMessageListenerImpl(PaymentRequestHandler paymentRequestHandler, PaymentCompletedMessagePublisher paymentCompletedMessagePublisher, PaymentCancelledMessagePublisher paymentCancelledMessagePublisher, PaymentFailedMessagePublisher paymentFailedMessagePublisher) {
    //    this.paymentRequestHandler = paymentRequestHandler;
    //    this.paymentCompletedMessagePublisher = paymentCompletedMessagePublisher;
    //    this.paymentCancelledMessagePublisher = paymentCancelledMessagePublisher;
    //    this.paymentFailedMessagePublisher = paymentFailedMessagePublisher;
    //}

    public PaymentRequestMessageListenerImpl(PaymentRequestHandler paymentRequestHandler) {
        this.paymentRequestHandler = paymentRequestHandler;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHandler.completePayment(paymentRequest);
        //publishEvent(paymentEvent);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent =
                paymentRequestHandler.cancelPayment(paymentRequest);
        //publishEvent(paymentEvent);
    }

    //public void publishEvent(PaymentEvent paymentEvent) {
    //    log.info("Publishing event with payment id: {} and order id: {}",
    //            paymentEvent.getPayment().getPaymentId(),
    //            paymentEvent.getPayment().getOrderId());
    //
    //    if(paymentEvent instanceof PaymentCompletedEvent) {
    //        paymentCompletedMessagePublisher.publish((PaymentCompletedEvent) paymentEvent);
    //    } else if (paymentEvent instanceof PaymentCancelledEvent) {
    //        paymentCancelledMessagePublisher.publish((PaymentCancelledEvent) paymentEvent);
    //    } else if (paymentEvent instanceof PaymentFailedEvent) {
    //        paymentFailedMessagePublisher.publish((PaymentFailedEvent) paymentEvent);
    //    }
    //}
}
