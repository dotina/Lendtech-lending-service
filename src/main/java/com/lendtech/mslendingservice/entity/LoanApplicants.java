package com.lendtech.mslendingservice.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@Table("tbl_loan_applicant")
public class LoanApplicants extends BaseModel implements Serializable {

    private String firstName;
    private String lastName;

    private String email;
    private String msisdn;

    private String documentNumber;

    private String documentType;

    private String conversationId;
    private Long statusId;

    private Long deletedStatusId;

    private String remarks;

    public LoanApplicants() {
    }

    public LoanApplicants(String firstName, String lastName, String email, String msisdn, String documentNumber, String documentType, String conversationId, String remarks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.msisdn = msisdn;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.conversationId = conversationId;
        this.statusId = 1L;
        this.remarks = remarks;

        this.setCreatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        this.setUpdatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
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

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }


    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getDeletedStatusId() {
        return deletedStatusId;
    }

    public void setDeletedStatusId(Long deletedStatusId) {
        this.deletedStatusId = deletedStatusId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
