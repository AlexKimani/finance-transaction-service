package com.kimani.finance.transaction.service.api.models.response;

import com.kimani.finance.transaction.service.database.models.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class AccountDetailsResponse {
    private long accountId;
    private long userId;
    private String accountType;
    private Double actualBalance;
    private Double availableBalance;
    private Status status;
}