package com.kimani.finance.transaction.service.database.models;

import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "transactions")
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions extends AbstractValues {
    private Long destinationAccount;
    private Double transactionAmount;
    private Double transactionTax;
    private String transactionReferenceId;
    private String transactionReceipt;
}
