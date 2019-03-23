package com.company.model;

import java.util.Objects;

public class Transaction {

    private Long invoiceId;

    private String userId;

    private String customerId;

    private Double amount;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction that = (Transaction) o;
        return Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, userId, customerId, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "invoiceId=" + invoiceId +
                ", userId='" + userId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
