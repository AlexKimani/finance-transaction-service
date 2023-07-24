package com.kimani.finance.transaction.service.api.controllers;

import com.kimani.finance.transaction.service.api.models.request.AccountConfigsRequest;
import com.kimani.finance.transaction.service.api.models.response.AccountConfigsResponse;
import com.kimani.finance.transaction.service.api.service.AccountsConfigsService;
import com.kimani.finance.transaction.service.database.models.AccountConfigs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/configs/account")
@RequiredArgsConstructor
public class AccountsConfigsController {
    private final AccountsConfigsService accountsConfigsService;

    @GetMapping("/all")
    public Flux<AccountConfigs> findAllAccountConfigs() {
        return accountsConfigsService.findAllAccountConfigs();
    }

    @GetMapping("/get/{id}")
    public Mono<AccountConfigsResponse> findAccountConfigById(@PathVariable long id) {
        return accountsConfigsService.findAccountConfigById(id);
    }

    @GetMapping("/get")
    public Mono<AccountConfigsResponse> findAccountConfigByAccountType(@RequestParam("type") String accountType) {
        return accountsConfigsService.findAccountConfigByAccountType(accountType);
    }

    @PostMapping("/create")
    public Mono<AccountConfigsResponse> createAccountConfig(@RequestBody AccountConfigsRequest accountConfigsRequest) {
        return accountsConfigsService.createAccountConfig(accountConfigsRequest);
    }

    @PutMapping("/update/{id}")
    public Mono<AccountConfigsResponse> updateAccountConfig(@PathVariable long id,
                                                            @RequestBody AccountConfigsRequest accountConfigsRequest) {
        return accountsConfigsService.updateAccountConfig(id, accountConfigsRequest);
    }
}
