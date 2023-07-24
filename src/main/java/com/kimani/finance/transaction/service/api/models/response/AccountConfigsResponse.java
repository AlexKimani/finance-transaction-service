package com.kimani.finance.transaction.service.api.models.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountConfigsResponse {
    private Long id;
    private String accountType;
    private Double accountMinimumLimit;
    private Double accountMaximumLimit;
}
