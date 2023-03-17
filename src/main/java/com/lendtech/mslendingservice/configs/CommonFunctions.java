package com.lendtech.mslendingservice.configs;

import com.lendtech.mslendingservice.models.payloads.api.ApiResponse;
import com.lendtech.mslendingservice.utilities.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.lendtech.mslendingservice.utilities.GlobalVariables.*;
import static com.lendtech.mslendingservice.utilities.Utilities.generateTrackingID;

@Component
public class CommonFunctions {
    private final Validations validations;

    @Autowired
    public CommonFunctions(Validations validations) {
        this.validations = validations;
    }

    public Mono<ApiResponse> processValidation(HttpHeaders httpHeaders) {

        return this.validations.validateHeaders(httpHeaders).flatMap(headerErrorMessage -> {

            if (!headerErrorMessage.getInvalidHeaderErrors().isEmpty() || headerErrorMessage.isMissingHeaders()) {
                return Mono.just(ApiResponse.responseFormatter(generateTrackingID(), RESPONSE_CODE_400, RESPONSE_FAILED,
                        RESPONSE_INVALID_HEADERS, headerErrorMessage));
            }

            String referenceId = Objects.requireNonNull(httpHeaders.get(X_CORRELATION_CONVERSATION_ID)).get(0);
            String sourceSystem = Objects.requireNonNull(httpHeaders.get(X_SOURCE_SYSTEM)).get(0);


            return Mono.just(ApiResponse.responseFormatter(referenceId, RESPONSE_CODE_0, "",
                    "", sourceSystem));

        }).switchIfEmpty(
                Mono.just(ApiResponse.responseFormatter(generateTrackingID(), RESPONSE_CODE_500, RESPONSE_FAILED,
                        RESPONSE_SERVICE_UNREACHABLE, null))
        );
    }
}
