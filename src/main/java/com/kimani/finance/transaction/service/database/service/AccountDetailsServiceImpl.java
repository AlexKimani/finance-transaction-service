package com.kimani.finance.transaction.service.database.service;

import com.kimani.finance.transaction.service.api.models.request.AccountDetailsRequest;
import com.kimani.finance.transaction.service.api.models.response.AccountDetailsResponse;
import com.kimani.finance.transaction.service.api.service.AccountsConfigsService;
import com.kimani.finance.transaction.service.database.models.AccountDetails;
import com.kimani.finance.transaction.service.database.models.Status;
import com.kimani.finance.transaction.service.database.repositories.AccountDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Account details service.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountDetailsServiceImpl {
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountsConfigsService accountsConfigsService;

    /**
     * Find account details by id mono.
     *
     * @param accountId the account id
     * @return the mono
     */
    public Mono<AccountDetailsResponse> findAccountDetailsById(long accountId) {
        return accountDetailsRepository.findAccountDetailsById(accountId)
                .flatMap(accountDetails -> accountsConfigsService.findAccountConfigById(accountDetails.getAccountTypeId())
                        .flatMap(accountConfigsResponse -> setAccountDetailsResponse(accountConfigsResponse.getAccountType(), accountDetails))
                        .switchIfEmpty(setAccountDetailsResponse("", accountDetails))
                        .onErrorResume(this::logError)
                ).switchIfEmpty(Mono.empty())
                .onErrorResume(this::logError);
    }

    /**
     * Find account details by user id mono.
     *
     * @param userId the user id
     * @return the mono
     */
    public Mono<List<AccountDetailsResponse>> findAccountDetailsByUserId(long userId) {
        return accountDetailsRepository.findAccountDetailsByUserId(userId).flatMap(accountDetailsList -> {
            List<AccountDetailsResponse> accountDetailsResponses = new ArrayList<>();
            accountDetailsList.forEach(accountDetails -> {
                String accountType = getAccountType(accountDetails.getAccountTypeId()).block();
                AccountDetailsResponse accountDetailsResponse = AccountDetailsResponse.builder()
                        .accountId(accountDetails.getId())
                        .userId(accountDetails.getUserId())
                        .accountType(accountType)
                        .actualBalance(accountDetails.getActualBalance())
                        .availableBalance(accountDetails.getAvailableBalance())
                        .build();
                accountDetailsResponses.add(accountDetailsResponse);
            });
            return Mono.justOrEmpty(accountDetailsResponses);
        }).onErrorResume(error -> {
            log.error("Exceptions occurred fetching Accounts for userId {}: \n Exception: ", userId, error);
            return Mono.empty();
        });
    }

    /**
     * Find account details by account type id and user id mono.
     *
     * @param accountTypeId the account type id
     * @param userId        the user id
     * @return the mono
     */
    public Mono<AccountDetailsResponse> findAccountDetailsByAccountTypeIdAndUserId(long accountTypeId, long userId) {
        return getAccountType(accountTypeId).flatMap(accountType ->
                accountDetailsRepository.findAccountDetailsByAccountTypeIdAndUserId(accountTypeId, userId)
                    .flatMap(accountDetails -> setAccountDetailsResponse(accountType, accountDetails))
                    .onErrorResume(this::logError)
                .switchIfEmpty(Mono.empty())
        ).onErrorResume(error -> {
            log.error("Exception occurred while getting account type: ",error);
            return Mono.empty();
        });
    }

    /**
     * Create new account details mono.
     *
     * @param accountDetailsRequest the account details request
     * @return the mono
     */
    public Mono<AccountDetailsResponse> createNewAccountDetails(AccountDetailsRequest accountDetailsRequest) {
        return findAccountDetailsByAccountTypeIdAndUserId(accountDetailsRequest.getAccountTypeId(), accountDetailsRequest.getUserId())
                .switchIfEmpty(createNewAccount(accountDetailsRequest))
                .onErrorResume(this::logError)
                .flatMap(Mono::justOrEmpty);
    }

    /**
     * Update account balances mono.
     *
     * @param accountTypeId    the account type id
     * @param userId           the user id
     * @param actualBalance    the actual balance
     * @param availableBalance the available balance
     * @return the mono
     */
    public Mono<AccountDetailsResponse> updateAccountBalances(long accountTypeId, long userId, double actualBalance, double availableBalance) {
        return getAccountType(accountTypeId).flatMap(accountType ->
                    accountDetailsRepository.findAccountDetailsByAccountTypeIdAndUserId(accountTypeId, userId)
                    .flatMap(accountDetails -> {
                        accountDetails.setActualBalance(actualBalance);
                        accountDetails.setAvailableBalance(availableBalance);
                        return accountDetailsRepository.save(accountDetails)
                                .flatMap(accountDetailsDB -> setAccountDetailsResponse(accountType, accountDetailsDB))
                                .onErrorResume(this::logError);
                    }).switchIfEmpty(Mono.defer(() -> {
                        log.info("No Account details for userId {} and accountTypeId {}", userId, accountTypeId);
                        return Mono.empty();
                    })).onErrorResume(this::logError)
                ).onErrorResume(error -> {
                    log.error("Exception occurred while getting account type: ",error);
                    return Mono.empty();
                });
    }

    /**
     * Update account status mono.
     *
     * @param accountId the account id
     * @param status    the status
     * @return the mono
     */
    public Mono<AccountDetailsResponse> updateAccountStatus(long accountId, Status status) {
        return accountDetailsRepository.findAccountDetailsById(accountId)
                .flatMap(accountDetails -> getAccountType(accountDetails.getAccountTypeId()).flatMap(accountType -> {
                    accountDetails.setActive(status);
                    return accountDetailsRepository.save(accountDetails)
                            .flatMap(accountDetailsDB -> setAccountDetailsResponse(accountType, accountDetailsDB))
                            .onErrorResume(this::logError);
                }).onErrorResume(error -> {
                    log.error("Exception occurred while getting account type: ",error);
                    return Mono.empty();
                })).switchIfEmpty(Mono.defer(() -> {
                            log.info("No Account details for accountId {}", accountId);
                            return Mono.empty();
                        })
                ).onErrorResume(this::logError);
    }

    private Mono<AccountDetailsResponse> createNewAccount(AccountDetailsRequest accountDetailsRequest) {
        return getAccountType(accountDetailsRequest.getAccountTypeId())
                .flatMap(accountType -> createNewAccountDetailsInDB(accountDetailsRequest)
                    .flatMap(accountDetails ->
                        setAccountDetailsResponse(accountType, accountDetails)))
                .switchIfEmpty(Mono.defer(() -> {
                            log.info("No Account Type Id for accountTypeId {}", accountDetailsRequest.getAccountTypeId());
                            return Mono.empty();
                        })
                ).onErrorResume(error -> {
                    log.error("Exception occurred while getting account type: ",error);
                    return Mono.empty();
                });
    }

    private Mono<AccountDetails> createNewAccountDetailsInDB(AccountDetailsRequest accountDetailsRequest) {
        AccountDetails accountDetails = AccountDetails.builder()
                .accountTypeId(accountDetailsRequest.getAccountTypeId())
                .actualBalance(accountDetailsRequest.getActualBalance())
                .availableBalance(accountDetailsRequest.getAvailableBalance())
                .userId(accountDetailsRequest.getUserId())
                .build();
        return accountDetailsRepository.save(accountDetails);
    }

    private Mono<String> getAccountType(long accountTypeId) {
        return accountsConfigsService.findAccountConfigById(accountTypeId)
                .flatMap(accountConfigsResponse -> Mono.justOrEmpty(accountConfigsResponse.getAccountType()))
                .switchIfEmpty(Mono.empty());
    }

    private Mono<AccountDetailsResponse> setAccountDetailsResponse(String accountType, AccountDetails accountDetails) {
        return Mono.justOrEmpty(AccountDetailsResponse.builder()
                        .accountId(accountDetails.getId())
                        .userId(accountDetails.getUserId())
                        .accountType(accountType)
                        .actualBalance(accountDetails.getActualBalance())
                        .availableBalance(accountDetails.getAvailableBalance())
                        .status(accountDetails.getActive())
                .build());
    }

    private Mono<AccountDetailsResponse> logError(Throwable error) {
        log.error("Exception occurred: ", error);
        return Mono.empty();
    }
}
