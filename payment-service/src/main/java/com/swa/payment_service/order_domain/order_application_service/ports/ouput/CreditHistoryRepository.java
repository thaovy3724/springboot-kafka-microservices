package com.swa.payment_service.order_domain.order_application_service.ports.ouput;

import com.swa.payment_service.order_domain.order_domain_core.entity.CreditHistory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditHistoryRepository {
    CreditHistory save(CreditHistory creditHistory);
    Optional<List<CreditHistory>> findByCustomerID(UUID customerID);
}
