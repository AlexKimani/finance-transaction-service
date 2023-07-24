package com.kimani.finance.transaction.service.api.controllers;

import com.kimani.finance.transaction.service.api.models.request.AccountDetailsRequest;
import com.kimani.finance.transaction.service.api.models.response.ApiResponse;
import com.kimani.finance.transaction.service.api.service.AccountDetailsService;
import com.kimani.finance.transaction.service.database.models.Status;
import com.kimani.finance.transaction.service.utils.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path = "/v1/account")
@RequiredArgsConstructor
public class AccountDetailsController {
    private final AccountDetailsService accountDetailsService;
    private final Messages messages;

    @GetMapping("/id/{accountId}/get")
    public Mono<ApiResponse> getAccountDetailsByAccountId(@PathVariable long accountId) {
        return accountDetailsService.getAccountDetailsByAccountId(accountId)
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.defer(() -> messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null)))
                .onErrorResume(messages::logError);
    }

    @GetMapping("/user/{userId}/get")
    public Mono<ApiResponse> getAccountDetailsByUserId(@PathVariable long userId) {
        return accountDetailsService.getAccountDetailsByUserId(userId)
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.defer(() -> messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null)))
                .onErrorResume(messages::logError);
    }

    @GetMapping("/details/get")
    public Mono<ApiResponse> getAccountDetailsByAccountTypeIdAndUserId(@RequestParam long accountTypeId, @RequestParam long userId) {
        return accountDetailsService.getAccountDetailsByAccountTypeIdAndUserId(accountTypeId, userId)
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.defer(() -> messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null)))
                .onErrorResume(messages::logError);
    }

    @PostMapping("/details/create")
    public Mono<ApiResponse> createNewAccountDetails(@RequestBody AccountDetailsRequest accountDetailsRequest) {
        return accountDetailsService.createNewAccountDetails(accountDetailsRequest)
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.defer(() -> messages.setApiResponse(Messages.Status.STATUS_CODE_UNKNOWN_ISSUE.code, null)))
                .onErrorResume(messages::logError);
    }

    @PutMapping("/balances/update")
    public Mono<ApiResponse> updateAccountBalances(@RequestBody AccountDetailsRequest accountDetailsRequest) {
        return accountDetailsService.updateAccountBalances(accountDetailsRequest)
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.defer(() -> messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null)))
                .onErrorResume(messages::logError);
    }

    @PatchMapping("/id/{accountId}/status")
    public Mono<ApiResponse> updateAccountStatus(@PathVariable long accountId, @RequestParam Status status) {
        return accountDetailsService.updateAccountStatus(accountId, status)
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.defer(() -> messages.setApiResponse(Messages.Status.STATUS_CODE_NOT_FOUND.code, null)))
                .onErrorResume(messages::logError);
    }
}
