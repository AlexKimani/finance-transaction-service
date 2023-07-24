package com.kimani.finance.transaction.service.database.repositories;

import com.kimani.finance.transaction.service.database.models.AccountDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface AccountDetailsRepository extends R2dbcRepository<AccountDetails, Long> {
    Mono<AccountDetails> findAccountDetailsById(long id);

    Mono<AccountDetails> findAccountDetailsByAccountTypeIdAndUserId(long accountType, long userId);

    @Query("SELECT * FROM account_details WHERE user_id = :userId")
    Mono<List<AccountDetails>> findAccountDetailsByUserId(@Param("userId") long userId);

    Flux<AccountDetails> findAllByUserId(long userId);
    Flux<AccountDetails> findAccountDetailsByAccountTypeId(long accountTypeId);
}
