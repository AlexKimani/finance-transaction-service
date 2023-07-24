package com.kimani.finance.transaction.service.api.models.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDetailsResponse {
    private String surname;
    private String otherNames;
    private Long idNumber;
    private Long phoneNumber;
    private String userIdentity;
}
