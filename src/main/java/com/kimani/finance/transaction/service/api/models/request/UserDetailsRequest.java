package com.kimani.finance.transaction.service.api.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsRequest {
    private String firstName;
    private String otherNames;
    private long idNumber;
    private long phoneNumber;
}
