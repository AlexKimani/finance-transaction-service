package com.kimani.finance.transaction.service.database.models;

import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "account_configs")
@Setter
@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountConfigs extends AbstractValues {
    private String accountType;
    private Double accountMinimumLimit;
    private Double accountMaximumLimit;
}
