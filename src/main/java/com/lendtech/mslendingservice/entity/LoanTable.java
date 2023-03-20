package com.lendtech.mslendingservice.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("tbl_loan")
public class LoanTable extends BaseModel{
    private String conversationId;
    @Column("active_status")
    private Boolean isActiveStatus;

    private Long deletedStatusId;

    private String remarks;

    private Double creditScore;
    private Double installmentAmount;
    private Double loanAmountBalance;
    private LocalDateTime nextDueDate;
    private LocalDateTime loanCompletionDate;
    private LocalDateTime loanCreationDate;
    private Double loanLimit;
    private Double principleAmount;
    private Double disbursedAmount;

    private String bankLendingTransactionId;
    private Long loanApplicantId;
    private Double loanInterestRate;

    public LoanTable() {
    }

    public LoanTable(String conversationId, Boolean isActiveStatus,  String remarks, Double creditScore, Double installmentAmount,
                     Double loanAmountBalance, LocalDateTime loanCompletionDate, LocalDateTime loanCreationDate, Double loanLimit,
                     Double principleAmount, Double disbursedAmount, String bankLendingTransactionId, Long loanApplicantId,
                     Double loanInterestRate, LocalDateTime nextDueDate) {
        this.conversationId = conversationId;
        this.isActiveStatus = isActiveStatus;
        this.remarks = remarks;
        this.creditScore = creditScore;
        this.installmentAmount = installmentAmount;
        this.loanAmountBalance = loanAmountBalance;
        this.loanCompletionDate = loanCompletionDate;
        this.loanCreationDate = loanCreationDate;
        this.loanLimit = loanLimit;
        this.principleAmount = principleAmount;
        this.disbursedAmount = disbursedAmount;
        this.bankLendingTransactionId = bankLendingTransactionId;
        this.loanApplicantId = loanApplicantId;
        this.loanInterestRate = loanInterestRate;
        this.nextDueDate = nextDueDate;
    }

    public LocalDateTime getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDateTime nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Double getLoanInterestRate() {
        return loanInterestRate;
    }

    public void setLoanInterestRate(Double loanInterestRate) {
        this.loanInterestRate = loanInterestRate;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Boolean getActiveStatus() {
        return isActiveStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        isActiveStatus = activeStatus;
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

    public Double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Double creditScore) {
        this.creditScore = creditScore;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getLoanAmountBalance() {
        return loanAmountBalance;
    }

    public void setLoanAmountBalance(Double loanAmountBalance) {
        this.loanAmountBalance = loanAmountBalance;
    }

    public LocalDateTime getLoanCompletionDate() {
        return loanCompletionDate;
    }

    public void setLoanCompletionDate(LocalDateTime loanCompletionDate) {
        this.loanCompletionDate = loanCompletionDate;
    }

    public LocalDateTime getLoanCreationDate() {
        return loanCreationDate;
    }

    public void setLoanCreationDate(LocalDateTime loanCreationDate) {
        this.loanCreationDate = loanCreationDate;
    }

    public Double getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(Double loanLimit) {
        this.loanLimit = loanLimit;
    }

    public Double getPrincipleAmount() {
        return principleAmount;
    }

    public void setPrincipleAmount(Double principleAmount) {
        this.principleAmount = principleAmount;
    }

    public Double getDisbursedAmount() {
        return disbursedAmount;
    }

    public void setDisbursedAmount(Double disbursedAmount) {
        this.disbursedAmount = disbursedAmount;
    }

    public String getBankLendingTransactionId() {
        return bankLendingTransactionId;
    }

    public void setBankLendingTransactionId(String bankLendingTransactionId) {
        this.bankLendingTransactionId = bankLendingTransactionId;
    }

    public Long getLoanApplicantId() {
        return loanApplicantId;
    }

    public void setLoanApplicantId(Long loanApplicantId) {
        this.loanApplicantId = loanApplicantId;
    }
}
