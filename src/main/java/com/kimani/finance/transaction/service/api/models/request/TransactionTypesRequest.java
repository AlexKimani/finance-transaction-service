package com.kimani.finance.transaction.service.api.models.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class TransactionTypesRequest {
    private String transactionType;
    private Double transactionFee;
    private Double transactionTax;
}
