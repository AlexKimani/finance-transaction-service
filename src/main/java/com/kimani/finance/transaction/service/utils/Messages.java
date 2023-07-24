package com.kimani.finance.transaction.service.utils;

import com.kimani.finance.transaction.service.api.models.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Messages {
    private final Map<Integer, String> messageMap = new HashMap<>();

    private String getStatusMessage(int statusCode) {
        setMessageMap();
        return messageMap.getOrDefault(statusCode, "");
    }

    public Mono<ApiResponse> setApiResponse(int statusCode, Object data) {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(statusCode)
                .message(getStatusMessage(statusCode))
                .data(data)
                .build();
        return Mono.justOrEmpty(apiResponse);
    }

    public Mono<ApiResponse> logError(Throwable throwable) {
        log.error("Exception occurred: ", throwable);
        return setApiResponse(Messages.Status.STATUS_CODE_ERROR.code, null);
    }
    private void setMessageMap() {
        messageMap.put(500, "Error occurred while processing the request.");
        messageMap.put(1000, "Successfully Processed Request.");
        messageMap.put(1001, "Successfully created new details.");
        messageMap.put(1002, "Successfully updated the existing details.");
        messageMap.put(1011, "The data Posted already exists in the system.");
        messageMap.put(1012, "The request data does not match the expected format.");
        messageMap.put(1013, "The data requested does not exist in the system, try creating it.");
        messageMap.put(1014, "An unknown issue occurred while processing your request, check logs to see more information.");
        messageMap.put(1015, "Dear customer, the available balance is not sufficient. Kindly top-up your account.");
    }

    public enum Status {
        STATUS_CODE_ERROR(500),
        STATUS_CODE_SUCCESS(1000),
        STATUS_CODE_CREATED(1001),
        STATUS_CODE_UPDATED(1002),
        STATUS_CODE_ALREADY_EXISTS(1011),
        STATUS_CODE_BAD_REQUEST(1012),
        STATUS_CODE_NOT_FOUND(1013),
        STATUS_CODE_UNKNOWN_ISSUE(1014),
        STATUS_CODE_INSUFFICIENT_BALANCE(1015);
        Status(int code) {
            this.code = code;
        }

        public final int code;
    }
}
