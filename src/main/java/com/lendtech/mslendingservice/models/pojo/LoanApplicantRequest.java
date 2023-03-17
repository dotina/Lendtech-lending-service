package com.lendtech.mslendingservice.models.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "firstName",
        "lastName",
        "msisdn",
        "email",
        "termsConditions",
        "documentNumber",
        "documentType",

})
public class LoanApplicantRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String documentNumber;

    private String documentType;
    @NotNull(message = "Invalid Request")
    @Pattern(regexp = "^(254|0)?[71]\\d{8}$", message = "Invalid Body Msisdn")
    @JsonProperty("msisdn")
    private String msisdn;

    @NotNull(message = "Invalid Request")
    @JsonProperty("termsConditions")
    private boolean termsConditions;

    public LoanApplicantRequest() {
    }

    public LoanApplicantRequest(String firstName, String lastName, String email, String documentNumber, String documentType, String msisdn, boolean termsConditions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.msisdn = msisdn;
        this.termsConditions = termsConditions;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(boolean termsConditions) {
        this.termsConditions = termsConditions;
    }
}
