package com.kimani.finance.transaction.service.api.service;

import com.kimani.finance.transaction.service.api.models.request.AccountConfigsRequest;
import com.kimani.finance.transaction.service.api.models.response.AccountConfigsResponse;
import com.kimani.finance.transaction.service.database.models.AccountConfigs;
import com.kimani.finance.transaction.service.database.service.AccountConfigsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountsConfigsService {
    private final AccountConfigsServiceImpl accountConfigsServiceImpl;

    public Flux<AccountConfigs> findAllAccountConfigs() {
        return accountConfigsServiceImpl.findAllAccountConfigs();
    }

    public Mono<AccountConfigsResponse> findAccountConfigById(long id) {
        return accountConfigsServiceImpl.findAccountConfigById(id);
    }

    public Mono<AccountConfigsResponse> findAccountConfigByAccountType(String accountType) {
        return accountConfigsServiceImpl.findAccountConfigByAccountType(accountType);
    }

    public Mono<AccountConfigsResponse> createAccountConfig(AccountConfigsRequest accountConfigsRequest) {
        return accountConfigsServiceImpl.createAccountConfig(accountConfigsRequest);
    }

    public Mono<AccountConfigsResponse> updateAccountConfig(long id, AccountConfigsRequest accountConfigsRequest) {
        return accountConfigsServiceImpl.updateAccountConfig(id, accountConfigsRequest);
    }
}
