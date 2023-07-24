package com.kimani.finance.transaction.service.database.repositories;

import com.kimani.finance.transaction.service.database.models.AccountConfigs;
import lombok.NonNull;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountConfigsRepository extends R2dbcRepository<AccountConfigs, Long> {
    Mono<AccountConfigs> findAccountConfigsDaoByAccountTypeEqualsIgnoreCase(@NonNull String accountType);

    Mono<AccountConfigs> findAccountConfigsDaoByIdEquals(long id);
}
