package com.kimani.finance.transaction.service.database.models;

import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "transaction_types")
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTypes extends AbstractValues {
    private String transactionType;
    private Double transactionFee;
    private Double transactionTax;
}
