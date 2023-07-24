package com.kimani.finance.transaction.service.database.models;

import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "account_details")
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails extends AbstractValues {
    private long userId;
    private long accountTypeId;
    private Double actualBalance;
    private Double availableBalance;
    private Status active;
}
