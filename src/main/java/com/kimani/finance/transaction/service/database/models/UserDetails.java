package com.kimani.finance.transaction.service.database.models;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "user_details")
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails extends AbstractValues {
    private String surname;
    private String otherNames;
    private Long idNumber;
    private Long phoneNumber;
    private String userIdentity;
}
