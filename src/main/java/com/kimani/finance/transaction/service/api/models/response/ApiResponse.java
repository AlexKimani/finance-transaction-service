package com.kimani.finance.transaction.service.api.models.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ApiResponse {
    private int statusCode;
    private String message;
    private Object data;
}
