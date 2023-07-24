package com.kimani.finance.transaction.service.database.service;

import com.kimani.finance.transaction.service.api.models.request.AccountConfigsRequest;
import com.kimani.finance.transaction.service.api.models.response.AccountConfigsResponse;
import com.kimani.finance.transaction.service.database.models.AccountConfigs;
import com.kimani.finance.transaction.service.database.repositories.AccountConfigsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Account configs service.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountConfigsServiceImpl {
    private final AccountConfigsRepository accountConfigsRepository;

    /**
     * Find all account configs flux.
     *
     * @return the flux
     */
    public Flux<AccountConfigs> findAllAccountConfigs() {
        return accountConfigsRepository.findAll(Sort.by("id"))
                .onErrorResume(error -> {
                    log.error("Exception occurred while getting All Account Configs: ", error);
                    return Mono.empty();
                });
    }

    /**
     * Find account config by id mono.
     *
     * @param id the id
     * @return the mono
     */
    public Mono<AccountConfigsResponse> findAccountConfigById(long id) {
        return accountConfigsRepository.findAccountConfigsDaoByIdEquals(id)
                .flatMap(this::createAccountConfigsResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(this::logError);
    }

    /**
     * Find account config by account type mono.
     *
     * @param accountType the account type
     * @return the mono
     */
    public Mono<AccountConfigsResponse> findAccountConfigByAccountType(String accountType) {
        return accountConfigsRepository.findAccountConfigsDaoByAccountTypeEqualsIgnoreCase(accountType)
                .flatMap(this::createAccountConfigsResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(this::logError);
    }

    /**
     * Create account config mono.
     *
     * @param accountConfigsRequest the account configs request
     * @return the mono
     */
    public Mono<AccountConfigsResponse> createAccountConfig(AccountConfigsRequest accountConfigsRequest) {
        return accountConfigsRepository.findAccountConfigsDaoByAccountTypeEqualsIgnoreCase(accountConfigsRequest.getAccountType())
                .flatMap(this::createAccountConfigsResponse)
                .switchIfEmpty(createNewAccountConfig(accountConfigsRequest))
                .onErrorResume(this::logError);
    }

    /**
     * Update account config mono.
     *
     * @param id                    the id
     * @param accountConfigsRequest the account configs request
     * @return the mono
     */
    public Mono<AccountConfigsResponse> updateAccountConfig(long id, AccountConfigsRequest accountConfigsRequest) {
        return accountConfigsRepository.findAccountConfigsDaoByIdEquals(id)
                .flatMap(accountConfigs -> {
                    AccountConfigs accountConfigs1 = AccountConfigs.builder()
                            .accountMaximumLimit(accountConfigsRequest.getAccountMaximumLimit())
                            .accountMinimumLimit(accountConfigsRequest.getAccountMinimumLimit())
                            .accountType(accountConfigsRequest.getAccountType())
                            .build();
                    accountConfigs1.setId(accountConfigs.getId());
                    accountConfigs1.setCreatedAt(accountConfigs.getCreatedAt());
                    return accountConfigsRepository.save(accountConfigs1)
                            .flatMap(this::createAccountConfigsResponse);
                })
                .switchIfEmpty(createNewAccountConfig(accountConfigsRequest))
             .onErrorResume(this::logError);
    }

    private Mono<AccountConfigsResponse> createNewAccountConfig(AccountConfigsRequest accountConfigsRequest) {
        AccountConfigs accountConfigs = AccountConfigs.builder()
                .accountMaximumLimit(accountConfigsRequest.getAccountMaximumLimit())
                .accountMinimumLimit(accountConfigsRequest.getAccountMinimumLimit())
                .accountType(accountConfigsRequest.getAccountType())
                .build();
        return accountConfigsRepository.save(accountConfigs)
                .flatMap(this::createAccountConfigsResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(this::logError);
    }

    private Mono<AccountConfigsResponse> createAccountConfigsResponse(AccountConfigs accountConfigs) {
        return Mono.just(AccountConfigsResponse.builder()
                .id(accountConfigs.getId())
                .accountMinimumLimit(accountConfigs.getAccountMinimumLimit())
                .accountMaximumLimit(accountConfigs.getAccountMaximumLimit())
                .accountType(accountConfigs.getAccountType())
                .build());
    }

    private Mono<AccountConfigsResponse> logError(Throwable error) {
        log.error("Exception occurred: ", error);
        return Mono.empty();
    }
}
