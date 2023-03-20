package com.lendtech.mslendingservice.service;

import com.lendtech.mslendingservice.configs.CommonFunctions;
import com.lendtech.mslendingservice.exceptions.CustomException;
import com.lendtech.mslendingservice.models.payloads.api.ApiResponse;
import com.lendtech.mslendingservice.models.pojo.LoanRepaymentRequest;
import com.lendtech.mslendingservice.models.pojo.SmsRequest;
import com.lendtech.mslendingservice.repository.LoanApplicantRepository;
import com.lendtech.mslendingservice.repository.LoanRepository;
import com.lendtech.mslendingservice.repository.LoanTransactionRepository;
import com.lendtech.mslendingservice.utilities.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.lendtech.mslendingservice.utilities.GlobalVariables.*;
import static com.lendtech.mslendingservice.utilities.GlobalVariables.HEADER_MSG;
import static com.lendtech.mslendingservice.utilities.Utilities.*;

@Service
public class LoanRepaymentService {
    private final CommonFunctions commonFunctions;
    private final LoanRepository loanRepository;
    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanApplicantRepository loanApplicantRepository;
    private final SmsService smsService;

    @Autowired
    public LoanRepaymentService(CommonFunctions commonFunctions, LoanRepository loanRepository, LoanTransactionRepository loanTransactionRepository, LoanApplicantRepository loanApplicantRepository, SmsService smsService) {
        this.commonFunctions = commonFunctions;
        this.loanRepository = loanRepository;
        this.loanTransactionRepository = loanTransactionRepository;
        this.loanApplicantRepository = loanApplicantRepository;
        this.smsService = smsService;
    }

    public Mono<ResponseEntity<ApiResponse>> processLoanRepaymentRequest(HttpHeaders httpHeaders, LoanRepaymentRequest request, long startTime) {
        return commonFunctions.processValidation(httpHeaders).flatMap(apiResponse -> {
            if (apiResponse.getResponseHeader().getResponseCode() != 0) {
                LogManager.error(generateTrackingID(), HEADERS, PROCESS_HEADER, String.valueOf(System.currentTimeMillis() - startTime), request.getMsisdn()
                        , SOURCE, VAL, HEADER_RESPONSE, 400, HEADER_MSG,
                        "validating loan repayment err", "", "", null);
                return Mono.just(new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST));
            }
            String referenceId = apiResponse.getResponseHeader().getRequestRefId();
            String sourceSystem = apiResponse.getResponseBody().toString();

            return loanApplicantRepository.findByMsisdn(request.getMsisdn()).flatMap(loanApplicants -> {
                return loanRepository.findByLoanApplicantIdAndActiveStatusIsTrue(loanApplicants.getId()).flatMap(loanTable -> {
                    if (request.getAmount() > loanTable.getLoanAmountBalance()) return Mono.error(new CustomException("The Amount is greater then loan balance"));
                    // The B2B service done then below account adjustments
                    return loanTransactionRepository.save(commonFunctions.prepareLoanRepayment(loanTable.getId(),request,referenceId)).flatMap(loanTransaction -> {
                        loanTable.setLoanAmountBalance(loanTable.getLoanAmountBalance()-loanTransaction.getAmount());
                        if (loanTable.getLoanAmountBalance() == 0) {
                            loanTable.setActiveStatus(false);
                            loanTable.setRemarks("Loan fully paid and closed!!");
                        }
                        loanTable.setNextDueDate(nextDueMonth(loanTable.getNextDueDate(),1));
                        return loanRepository.save(loanTable).flatMap(loanTable1 -> {

                            smsService.sendSms(new SmsRequest(request.getMsisdn(),
                                    "Your have settled your monthly installments successfully. with loan reference -- "+loanTransaction.getBankLendingTransactionId()));

                            if (!loanTable1.getActiveStatus()){
                                smsService.sendSms(new SmsRequest(request.getMsisdn(),
                                        "Your have completed paying for your loan -- loan reference "+ loanTable1.getBankLendingTransactionId()));
                            }

                            LogManager.info(referenceId, TRANSACTION_TYPE, "loanRepayment", String.valueOf(System.currentTimeMillis() - startTime),
                                    request.getMsisdn(), sourceSystem, TARGET_SYSTEM_DB, RESPONSE_SUCCESSFUL,
                                    RESPONSE_CODE_200, RESPONSE_SUCCESS, "", parseToJsonString(request),
                                    parseToJsonString(loanTable), "");
                            return Mono.just(new ResponseEntity<>(ApiResponse.responseFormatter(referenceId,
                                    RESPONSE_CODE_200, RESPONSE_SUCCESS, "Applicant Created Successfully", loanTable),
                                    HttpStatus.OK));
                        });
                    });
                }).switchIfEmpty(Mono.defer(()->{
                    LogManager.error(referenceId, TRANSACTION_TYPE, "loanRepaymentErr", String.valueOf(System.currentTimeMillis() - startTime),
                            request.getMsisdn(), sourceSystem, TARGET_SYSTEM_DB, RESPONSE_FAILED,
                            RESPONSE_CODE_400, RESPONSE_FAILED, "There applicant has no active loan --- ", parseToJsonString(request),
                            "", "");
                    return Mono.just(new ResponseEntity<>(ApiResponse.responseFormatter(referenceId,
                            RESPONSE_CODE_400, RESPONSE_SUCCESS, "The applicant has no active loan", ""),
                            HttpStatus.OK));
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
