package com.swa.payment_service.order_domain.order_application_service.ports.ouput;

import com.swa.payment_service.order_domain.order_domain_core.entity.CreditEntry;

import java.util.Optional;
import java.util.UUID;

public interface CreditEntryRepository {
    CreditEntry save(CreditEntry creditEntry);
    Optional<CreditEntry> findByCustomerID(UUID customerID); // Nếu có trong DB -> return optional chứa object đó; còn không -> return Optional.empty()

}
