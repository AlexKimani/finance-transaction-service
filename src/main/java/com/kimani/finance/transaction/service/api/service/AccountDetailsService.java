package com.kimani.finance.transaction.service.api.service;

import com.kimani.finance.transaction.service.api.models.request.AccountDetailsRequest;
import com.kimani.finance.transaction.service.api.models.response.ApiResponse;
import com.kimani.finance.transaction.service.database.models.Status;
import com.kimani.finance.transaction.service.database.service.AccountDetailsServiceImpl;
import com.kimani.finance.transaction.service.utils.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * The type Account details service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDetailsService {
    private final AccountDetailsServiceImpl accountDetailsServiceImpl;
    private final Messages messages;

    /**
     * Gets account details by account id.
     *
     * @param accountId the account id
     * @return the account details by account id
     */
    public Mono<ApiResponse> getAccountDetailsByAccountId(long accountId) {
        return accountDetailsServiceImpl.findAccountDetailsById(accountId)
                .flatMap(accountDetailsResponse -> messages.setApiResponse(Messages.Status.STATUS_CODE_SUCCESS.code, accountDetailsResponse))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No data found for accountId: {}", accountId);
                    return messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null);
                }))
                .onErrorResume(messages::logError);
    }

    /**
     * Gets account details by user id.
     *
     * @param userId the user id
     * @return the account details by user id
     */
    public Mono<ApiResponse> getAccountDetailsByUserId(long userId) {
        return accountDetailsServiceImpl.findAccountDetailsByUserId(userId)
                .flatMap(accountDetailsResponses -> messages.setApiResponse(Messages.Status.STATUS_CODE_SUCCESS.code, accountDetailsResponses))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No data found for userId: {}", userId);
                    return messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null);
                }))
                .onErrorResume(messages::logError);
    }

    /**
     * Gets account details by account type id and user id.
     *
     * @param accountTypeId the account type id
     * @param userId        the user id
     * @return the account details by account type id and user id
     */
    public Mono<ApiResponse> getAccountDetailsByAccountTypeIdAndUserId(long accountTypeId, long userId) {
        return accountDetailsServiceImpl.findAccountDetailsByAccountTypeIdAndUserId(accountTypeId, userId)
                .flatMap(accountDetailsResponse -> messages.setApiResponse(Messages.Status.STATUS_CODE_SUCCESS.code, accountDetailsResponse))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No data found for userId: {} and accountTypeId: {}", userId, accountTypeId);
                    return messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null);
                }))
                .onErrorResume(messages::logError);
    }

    /**
     * Create new account details mono.
     *
     * @param accountDetailsRequest the account details request
     * @return the mono
     */
    public Mono<ApiResponse> createNewAccountDetails(AccountDetailsRequest accountDetailsRequest) {
        return accountDetailsServiceImpl.findAccountDetailsByAccountTypeIdAndUserId(accountDetailsRequest.getAccountTypeId(), accountDetailsRequest.getUserId())
                .flatMap(accountDetailsResponse -> messages.setApiResponse(Messages.Status.STATUS_CODE_ALREADY_EXISTS.code, accountDetailsResponse))
                .switchIfEmpty(Mono.defer(() -> accountDetailsServiceImpl.createNewAccountDetails(accountDetailsRequest)
                        .flatMap(accountDetailsResponse -> messages.setApiResponse(Messages.Status.STATUS_CODE_CREATED.code, accountDetailsResponse))
                        .switchIfEmpty(Mono.defer(() -> {
                            log.warn("Check Error logs. No Response received while creating account details for : {}", accountDetailsRequest);
                            return messages.setApiResponse(Messages.Status.STATUS_CODE_UNKNOWN_ISSUE.code, null);
                        }))
                        .onErrorResume(messages::logError)))
                .onErrorResume(messages::logError);
    }

    /**
     * Update account balances mono.
     *
     * @param accountDetailsRequest the account details request
     * @return the mono
     */
    public Mono<ApiResponse> updateAccountBalances(AccountDetailsRequest accountDetailsRequest) {
        return accountDetailsServiceImpl.updateAccountBalances(accountDetailsRequest.getAccountTypeId(), accountDetailsRequest.getUserId(),
                accountDetailsRequest.getActualBalance(), accountDetailsRequest.getAvailableBalance())
                .flatMap(accountDetailsResponse -> messages.setApiResponse(Messages.Status.STATUS_CODE_UPDATED.code, accountDetailsResponse))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Check Error logs. No Response received while updating account details for : {}", accountDetailsRequest);
                    return messages.setApiResponse(Messages.Status.STATUS_CODE_UNKNOWN_ISSUE.code, null);
                }))
                .onErrorResume(messages::logError);
    }

    /**
     * Update account status mono.
     *
     * @param accountId the account id
     * @param status    the status
     * @return the mono
     */
    public Mono<ApiResponse> updateAccountStatus(long accountId, Status status) {
        return accountDetailsServiceImpl.updateAccountStatus(accountId, status)
                .flatMap(accountDetailsResponse -> messages.setApiResponse(Messages.Status.STATUS_CODE_UPDATED.code, accountDetailsResponse))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Check Error logs. No Response received while setting account status for accountId: {} with status: {}", accountId, status);
                    return messages.setApiResponse(Messages.Status.STATUS_CODE_UNKNOWN_ISSUE.code, null);
                }))
                .onErrorResume(messages::logError);
    }
}
