package com.food.ordering.system.payment.service.dataaccess.adapter;

import com.food.ordering.system.payment.service.dataaccess.entity.CreditHistoryEntity;
import com.food.ordering.system.payment.service.dataaccess.mapper.CreditHistoryDataAccessMapper;
import com.food.ordering.system.payment.service.dataaccess.repository.CreditHistoryJpaRepository;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper mapper;

    public CreditHistoryRepositoryImpl(CreditHistoryJpaRepository creditHistoryJpaRepository, CreditHistoryDataAccessMapper mapper) {
        this.creditHistoryJpaRepository = creditHistoryJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        CreditHistoryEntity creditHistoryEntity =
                mapper.creditHistoryToCreditHistoryEntity(creditHistory);
        CreditHistoryEntity savedCreditHistory =
                creditHistoryJpaRepository.save(creditHistoryEntity);
        return mapper.creditHistoryEntityToCreditHistory(savedCreditHistory);
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(UUID customerId) {
        Optional<List<CreditHistoryEntity>> creditHistoryEntities =
                creditHistoryJpaRepository.findByCustomerId(customerId);
        return creditHistoryEntities.map(
                creditHistoryList ->
                        creditHistoryList.stream()
                                .map(mapper::creditHistoryEntityToCreditHistory)
                                .toList()
        );
    }
}
