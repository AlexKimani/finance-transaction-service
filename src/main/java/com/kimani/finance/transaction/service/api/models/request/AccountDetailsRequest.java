package com.kimani.finance.transaction.service.api.models.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import reactor.util.annotation.NonNull;

@Data
@Getter
@Setter
public class AccountDetailsRequest {
    @NonNull
    private long userId;
    @NonNull
    private long accountTypeId;
    private Double actualBalance;
    private Double availableBalance;
}
