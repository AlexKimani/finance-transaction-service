package com.kimani.finance.transaction.service.api.models.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class TransactionTypesResponse {
    private Long id;
    private String transactionType;
    private Double transactionFee;
    private Double transactionTax;
}
