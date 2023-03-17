package com.lendtech.mslendingservice.service;


import com.lendtech.mslendingservice.configs.CommonFunctions;
import com.lendtech.mslendingservice.entity.LoanApplicants;
import com.lendtech.mslendingservice.models.payloads.api.ApiResponse;
import com.lendtech.mslendingservice.models.pojo.LoanApplicantRequest;
import com.lendtech.mslendingservice.models.pojo.SmsRequest;
import com.lendtech.mslendingservice.repository.LoanApplicantRepository;
import com.lendtech.mslendingservice.utilities.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.lendtech.mslendingservice.utilities.GlobalVariables.*;
import static com.lendtech.mslendingservice.utilities.Utilities.*;


@Service
public class LoanApplicantService {

    private final CommonFunctions commonFunctions;
    private final LoanApplicantRepository loanApplicantRepository;
    private final SmsService smsService;

    @Autowired
    public LoanApplicantService(CommonFunctions commonFunctions, LoanApplicantRepository loanApplicantRepository, SmsService smsService) {
        this.commonFunctions = commonFunctions;
        this.loanApplicantRepository = loanApplicantRepository;
        this.smsService = smsService;
    }

    public Mono<ResponseEntity<ApiResponse>> processApplicantOnboardingRequest(HttpHeaders httpHeaders, LoanApplicantRequest request, long startTime) {
        return commonFunctions.processValidation(httpHeaders).flatMap(apiResponse -> {
            if (apiResponse.getResponseHeader().getResponseCode() != 0) {
                LogManager.error(generateTrackingID(), HEADERS, PROCESS_HEADER, String.valueOf(System.currentTimeMillis() - startTime), request.getMsisdn()
                        , SOURCE, VAL, HEADER_RESPONSE, 400, HEADER_MSG,
                        "validating add elevator", "", "", null);
                return Mono.just(new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST));
            }
            String referenceId = apiResponse.getResponseHeader().getRequestRefId();
            String sourceSystem = apiResponse.getResponseBody().toString();

            return loanApplicantRepository.findByMsisdn(request.getMsisdn()).flatMap(loanApplicants -> {
                if (request.getEmail()!=null)loanApplicants.setEmail(request.getEmail());
                if (request.getFirstName()!=null)loanApplicants.setFirstName(request.getFirstName());
                if (request.getLastName()!=null)loanApplicants.setLastName(request.getLastName());
                if (request.getDocumentNumber()!=null)loanApplicants.setDocumentNumber(request.getDocumentNumber());
                if(request.getDocumentType()!=null) loanApplicants.setDocumentType(request.getDocumentType());

                return loanApplicantRepository.save(loanApplicants).flatMap(loanApplicants1 -> {
                    LogManager.info(referenceId, TRANSACTION_TYPE, "updatingApplicant", String.valueOf(System.currentTimeMillis() - startTime),
                            request.getMsisdn(), sourceSystem, TARGET_SYSTEM_DB, RESPONSE_SUCCESSFUL,
                            RESPONSE_CODE_200, RESPONSE_SUCCESS, "", parseToJsonString(request),
                            parseToJsonString(loanApplicants1), "");
                    return Mono.just(new ResponseEntity<>(ApiResponse.responseFormatter(referenceId,
                            RESPONSE_CODE_200, RESPONSE_SUCCESS, "Customer with MSISDN ".concat(request.getMsisdn()).concat("Already Exists! succesfull update done"), loanApplicants1),
                            HttpStatus.OK));
                });
            }).switchIfEmpty(Mono.defer(()->{
                    return loanApplicantRepository.save(new LoanApplicants(request.getFirstName(), request.getLastName(), request.getEmail(), request.getMsisdn(),
                            request.getDocumentNumber(), request.getDocumentType(), referenceId, "")).flatMap(loanApplicants -> {

                                smsService.sendSms(new SmsRequest(request.getMsisdn(),"You have been successfully enrolled as a loan applicant"));

                        LogManager.info(referenceId, TRANSACTION_TYPE, "updatingApplicant", String.valueOf(System.currentTimeMillis() - startTime),
                                request.getMsisdn(), sourceSystem, TARGET_SYSTEM_DB, RESPONSE_SUCCESSFUL,
                                RESPONSE_CODE_200, RESPONSE_SUCCESS, "", parseToJsonString(request),
                                parseToJsonString(loanApplicants), "");
                        return Mono.just(new ResponseEntity<>(ApiResponse.responseFormatter(referenceId,
                                RESPONSE_CODE_200, RESPONSE_SUCCESS, "Applicant Created Successfully", loanApplicants),
                                HttpStatus.OK));
                    });
            }));
        });
    }




}
