package com.lendtech.mslendingservice.controller;


import com.lendtech.mslendingservice.models.payloads.api.ApiResponse;
import com.lendtech.mslendingservice.models.pojo.LoanApplicantRequest;
import com.lendtech.mslendingservice.models.pojo.LoanRepaymentRequest;
import com.lendtech.mslendingservice.models.pojo.LoanRequest;
import com.lendtech.mslendingservice.service.LoanApplicantService;
import com.lendtech.mslendingservice.service.LoanRepaymentService;
import com.lendtech.mslendingservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1")
public class LendingController {

    private final LoanApplicantService loanApplicantService;
    private final LoanService loanService;
    private final LoanRepaymentService loanRepaymentService;

    @Autowired
    public LendingController(LoanApplicantService loanApplicantService, LoanService loanService, LoanRepaymentService loanRepaymentService) {
        this.loanApplicantService = loanApplicantService;
        this.loanService = loanService;
        this.loanRepaymentService = loanRepaymentService;
    }

    @PostMapping("/applicant/onboard")
    public Mono<ResponseEntity<ApiResponse>> serviceHandlerCreateLoanApplicant(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody LoanApplicantRequest requestBody){
        long startTime = System.currentTimeMillis();
        return loanApplicantService.processApplicantOnboardingRequest(httpHeaders,requestBody, startTime);
    }

    @PostMapping("/loan/create")
    public Mono<ResponseEntity<ApiResponse>> serviceHandlerCreateLoan(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody LoanRequest requestBody){
        long startTime = System.currentTimeMillis();
        return loanService.processLoanRequest(httpHeaders,requestBody, startTime);
    }

    @PostMapping("/loan/repayment")
    public Mono<ResponseEntity<ApiResponse>> serviceHandlerLoanRepayment(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody LoanRepaymentRequest requestBody){
        long startTime = System.currentTimeMillis();
        return loanRepaymentService.processLoanRepaymentRequest(httpHeaders,requestBody, startTime);
    }
}
