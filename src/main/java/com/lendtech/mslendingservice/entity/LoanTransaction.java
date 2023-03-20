package com.lendtech.mslendingservice.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table("tbl_transaction")
public class LoanTransaction extends BaseModel implements Serializable {
    private String conversationId;

    private Long deletedStatusId;

    private String remarks;

    private Double amount;
    private String bankLendingTransactionId;
    private Long loanId;

    public LoanTransaction() {
    }

    public LoanTransaction(String conversationId, String remarks, Double amount, String bankLendingTransactionId, Long loanId) {
        this.conversationId = conversationId;
        this.remarks = remarks;
        this.amount = amount;
        this.bankLendingTransactionId = bankLendingTransactionId;
        this.loanId = loanId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBankLendingTransactionId() {
        return bankLendingTransactionId;
    }

    public void setBankLendingTransactionId(String bankLendingTransactionId) {
        this.bankLendingTransactionId = bankLendingTransactionId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
}
