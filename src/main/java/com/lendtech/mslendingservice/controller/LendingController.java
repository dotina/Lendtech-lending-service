package com.lendtech.mslendingservice.controller;


import com.lendtech.mslendingservice.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1")
public class LendingController {

    private final ApiService apiService;

    @Autowired
    public LendingController(ApiService apiService) {
        this.apiService = apiService;
    }

}
