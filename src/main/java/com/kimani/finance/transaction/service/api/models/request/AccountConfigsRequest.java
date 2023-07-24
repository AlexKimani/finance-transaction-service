package com.kimani.finance.transaction.service.api.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountConfigsRequest {
    private String accountType;
    private Double accountMinimumLimit;
    private Double accountMaximumLimit;
}
