package com.kimani.finance.transaction.service.database.repositories;

import com.kimani.finance.transaction.service.database.models.TransactionTypes;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Repository
public interface TransactionTypesRepository extends R2dbcRepository<TransactionTypes, Long> {
    Mono<TransactionTypes> findTransactionTypesByIdEquals(long id);

    Mono<TransactionTypes> findTransactionTypesByTransactionTypeEqualsIgnoreCase(@NonNull String transactionType);
}
