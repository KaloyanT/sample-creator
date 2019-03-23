package com.company.model;

import java.util.Objects;

public class UserCustomerPair {

    private String userId;

    private String customerId;

    public UserCustomerPair(String userId, String customerId) {
        this.userId = userId;
        this.customerId = customerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCustomerPair that = (UserCustomerPair) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, customerId);
    }

    @Override
    public String toString() {
        return "UserCustomerPair{" +
                "userId='" + userId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
