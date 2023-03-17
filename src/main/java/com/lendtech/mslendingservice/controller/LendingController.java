package com.lendtech.mslendingservice.controller;


import com.lendtech.mslendingservice.models.payloads.api.ApiResponse;
import com.lendtech.mslendingservice.models.pojo.LoanApplicantRequest;
import com.lendtech.mslendingservice.service.LoanApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1")
public class LendingController {

    private final LoanApplicantService apiService;

    @Autowired
    public LendingController(LoanApplicantService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/applicant/onboard")
    public Mono<ResponseEntity<ApiResponse>> serviceHandlerCreateElevator(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody LoanApplicantRequest requestBody){
        long startTime = System.currentTimeMillis();
        return apiService.processApplicantOnboardingRequest(httpHeaders,requestBody, startTime);
    }
}
