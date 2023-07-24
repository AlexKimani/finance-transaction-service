package com.kimani.finance.transaction.service.database.repositories;

import com.kimani.finance.transaction.service.database.models.Transactions;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends R2dbcRepository<Transactions, Long> {
}
