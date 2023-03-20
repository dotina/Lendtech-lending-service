package com.lendtech.mslendingservice.models.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class LoanRepaymentRequest {
    @NotNull(message = "Invalid Request")
    @Pattern(regexp = "^(254|0)?[71]\\d{8}$", message = "Invalid Body Msisdn")
    @JsonProperty("msisdn")
    private String msisdn;

    @NotNull(message = "Invalid Entry")
    @Pattern(regexp = "^[0-9]*$", message = "only numbers allowed")
    private Double amount;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
