package com.swa.payment_service.order_domain.order_application_service.exception;

import com.swa.payment_service.order_domain.order_domain_core.exception.DomainException;

public class PaymentApplicationServiceException extends DomainException{
    public PaymentApplicationServiceException(String message) {
        super(message);
    }
}
