package com.lendtech.mslendingservice.models.pojo;

import jakarta.validation.constraints.NotBlank;

public class SmsRequest {
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String message;

    public SmsRequest() {
    }

    public SmsRequest(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
