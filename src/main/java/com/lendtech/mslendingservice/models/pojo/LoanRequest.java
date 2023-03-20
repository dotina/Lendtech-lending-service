package com.lendtech.mslendingservice.models.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class LoanRequest {
    @NotNull(message = "Invalid Request")
    @Pattern(regexp = "^(254|0)?[71]\\d{8}$", message = "Invalid Body Msisdn")
    @JsonProperty("msisdn")
    private String msisdn;
    @NotNull(message = "Invalid Entry")
    private Double loanInterestRate;
    @NotNull(message = "Invalid Entry")
    private Double principleAmount;
    private Double loanLimit;
    private Long loanDuration;
    private Double installmentAmount;
    private Double creditScore;
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Double getLoanInterestRate() {
        return loanInterestRate;
    }

    public void setLoanInterestRate(Double loanInterestRate) {
        this.loanInterestRate = loanInterestRate;
    }

    public Double getPrincipleAmount() {
        return principleAmount;
    }

    public void setPrincipleAmount(Double principleAmount) {
        this.principleAmount = principleAmount;
    }

    public Double getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(Double loanLimit) {
        this.loanLimit = loanLimit;
    }

    public Long getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Long loanDuration) {
        this.loanDuration = loanDuration;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Double creditScore) {
        this.creditScore = creditScore;
    }
}
