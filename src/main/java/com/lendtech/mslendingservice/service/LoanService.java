package com.lendtech.mslendingservice.service;

import com.lendtech.mslendingservice.configs.CommonFunctions;
import com.lendtech.mslendingservice.models.payloads.api.ApiResponse;
import com.lendtech.mslendingservice.models.pojo.LoanApplicantRequest;
import com.lendtech.mslendingservice.models.pojo.LoanRequest;
import com.lendtech.mslendingservice.repository.LoanApplicantRepository;
import com.lendtech.mslendingservice.repository.LoanRepository;
import com.lendtech.mslendingservice.utilities.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.lendtech.mslendingservice.utilities.GlobalVariables.*;
import static com.lendtech.mslendingservice.utilities.GlobalVariables.HEADER_MSG;
import static com.lendtech.mslendingservice.utilities.Utilities.generateTrackingID;
import static com.lendtech.mslendingservice.utilities.Utilities.parseToJsonString;

@Service
public class LoanService {
    private final CommonFunctions commonFunctions;
    private final LoanApplicantRepository loanApplicantRepository;
    private final LoanRepository loanRepository;
    private final SmsService smsService;

    @Autowired
    public LoanService(CommonFunctions commonFunctions, LoanApplicantRepository loanApplicantRepository, LoanRepository loanRepository, SmsService smsService) {
        this.commonFunctions = commonFunctions;
        this.loanApplicantRepository = loanApplicantRepository;
        this.loanRepository = loanRepository;
        this.smsService = smsService;
    }

    public Mono<ResponseEntity<ApiResponse>> processLoanRequest(HttpHeaders httpHeaders, LoanRequest request, long startTime) {
        return commonFunctions.processValidation(httpHeaders).flatMap(apiResponse -> {
            if (apiResponse.getResponseHeader().getResponseCode() != 0) {
                LogManager.error(generateTrackingID(), HEADERS, PROCESS_HEADER, String.valueOf(System.currentTimeMillis() - startTime), request.getMsisdn()
                        , SOURCE, VAL, HEADER_RESPONSE, 400, HEADER_MSG,
                        "validating loan creation err", "", "", null);
                return Mono.just(new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST));
            }
            String referenceId = apiResponse.getResponseHeader().getRequestRefId();
            String sourceSystem = apiResponse.getResponseBody().toString();

            return loanApplicantRepository.findByMsisdn(request.getMsisdn()).flatMap(loanApplicants -> {
                return loanRepository.findByLoanApplicantIdAndActiveStatusIsTrue(loanApplicants.getId()).flatMap(loanTable -> {
                    LogManager.error(referenceId, TRANSACTION_TYPE, "loanCreationErr", String.valueOf(System.currentTimeMillis() - startTime),
                            request.getMsisdn(), sourceSystem, TARGET_SYSTEM_DB, RESPONSE_FAILED,
                            RESPONSE_CODE_400, RESPONSE_FAILED, "There is an active running loan -- not closed", parseToJsonString(request),
                            "", "");
                    return Mono.just(new ResponseEntity<>(ApiResponse.responseFormatter(referenceId,
                            RESPONSE_CODE_400, RESPONSE_SUCCESS, "The applicant has an active loan", ""),
                            HttpStatus.OK));
                }).switchIfEmpty(Mono.defer(()->{
                    return loanRepository.save(commonFunctions.prepareLoan(loanApplicants.getId(), request, referenceId)).flatMap(loanTable -> {
                        LogManager.info(referenceId, TRANSACTION_TYPE, "loanCreation", String.valueOf(System.currentTimeMillis() - startTime),
                                request.getMsisdn(), sourceSystem, TARGET_SYSTEM_DB, RESPONSE_SUCCESSFUL,
                                RESPONSE_CODE_200, RESPONSE_SUCCESS, "", parseToJsonString(request),
                                parseToJsonString(loanTable), "");
                        return Mono.just(new ResponseEntity<>(ApiResponse.responseFormatter(referenceId,
                                RESPONSE_CODE_200, RESPONSE_SUCCESS, "Applicant Created Successfully", loanTable),
                                HttpStatus.OK));
                    });
                }));
            }).switchIfEmpty(Mono.defer(()->{
                LogManager.error(referenceId, TRANSACTION_TYPE, "loanCreationApplicantErr", String.valueOf(System.currentTimeMillis() - startTime),
                        request.getMsisdn(), sourceSystem, TARGET_SYSTEM_DB, RESPONSE_FAILED,
                        RESPONSE_CODE_400, RESPONSE_FAILED, "Applicant Does not Exist", parseToJsonString(request),
                        "", "");
                return Mono.just(new ResponseEntity<>(ApiResponse.responseFormatter(referenceId,
                        RESPONSE_CODE_400, RESPONSE_SUCCESS, "Applicant Does not Exist", ""),
                        HttpStatus.OK));
            }));
        });
    }
}
