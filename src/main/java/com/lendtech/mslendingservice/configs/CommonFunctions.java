package com.lendtech.mslendingservice.configs;

import com.lendtech.mslendingservice.entity.LoanTable;
import com.lendtech.mslendingservice.entity.LoanTransaction;
import com.lendtech.mslendingservice.models.payloads.api.ApiResponse;
import com.lendtech.mslendingservice.models.pojo.LoanRepaymentRequest;
import com.lendtech.mslendingservice.models.pojo.LoanRequest;
import com.lendtech.mslendingservice.utilities.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.lendtech.mslendingservice.utilities.GlobalVariables.*;
import static com.lendtech.mslendingservice.utilities.Utilities.*;

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

    /**
     * This prepatares the loan storage logic
     * @param applicantId this is the applicants foreign key
     * @param request this is the form items from the user
     * @param reference the reference that will be used also as a disbursment id since we don't have a disbursment API
     * @return the entiry values to be stored in the DB
     *  The assumption is that duration is in years
     */
    public LoanTable prepareLoan(Long applicantId, LoanRequest request, String reference){

        Double disbursedAmount = request.getPrincipleAmount() * ((1+(request.getLoanInterestRate()/100))*request.getLoanDuration());  // formula A = P (1+rt)
        LocalDateTime loanCompletionDate = nextDueYear(new Timestamp(System.currentTimeMillis()).toLocalDateTime(), request.getLoanDuration());
        LocalDateTime nextDueDate = nextDueMonth(new Timestamp(System.currentTimeMillis()).toLocalDateTime(), 1);

        return new LoanTable(reference, true,  "", request.getCreditScore(), request.getInstallmentAmount(), disbursedAmount,
                loanCompletionDate, new Timestamp(System.currentTimeMillis()).toLocalDateTime(),
                request.getLoanLimit(), request.getPrincipleAmount(), disbursedAmount, reference, applicantId, request.getLoanInterestRate(), nextDueDate);
    }

    public LoanTransaction prepareLoanRepayment(Long loanId, LoanRepaymentRequest request, String reference){
        return new LoanTransaction(reference, "Paying for loan ",request.getAmount(), reference, loanId);
    }
}
