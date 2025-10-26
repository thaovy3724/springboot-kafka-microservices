package com.food.ordering.system.payment.service.dataaccess.adapter;

import com.food.ordering.system.payment.service.dataaccess.entity.CreditEntryEntity;
import com.food.ordering.system.payment.service.dataaccess.mapper.CreditEntryDataAccessMapper;
import com.food.ordering.system.payment.service.dataaccess.repository.CreditEntryJpaRepository;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditEntryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {
    private final CreditEntryDataAccessMapper mapper;
    private final CreditEntryJpaRepository creditEntryJpaRepository;

    public CreditEntryRepositoryImpl(CreditEntryDataAccessMapper mapper, CreditEntryJpaRepository creditEntryJpaRepository) {
        this.mapper = mapper;
        this.creditEntryJpaRepository = creditEntryJpaRepository;
    }


    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        CreditEntryEntity creditEntryEntity =
                mapper.creditEntryToCreditEntryEntity(creditEntry);
        CreditEntryEntity savedCreditEntry =
                creditEntryJpaRepository.save(creditEntryEntity);
        return mapper.creditEntryEntityToCreditEntry(savedCreditEntry);
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(UUID customerId) {
        return creditEntryJpaRepository.findByCustomerId(customerId)
                .map(mapper::creditEntryEntityToCreditEntry);
    }
}
