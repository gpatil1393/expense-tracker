package com.cd.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class ExpenseDTO {

    @NotNull(message = "User ID cannot be null")
    private int userId;

    @NotNull(message = "Category cannot be null")
    private int categoryId;

    @NotBlank(message = "description is a required field")
    private String description;

    @NotNull
    @Positive(message = "Amount must be a valid positive number")
    private double amount;

    @NotNull(message = "Expense date cannot be null or empty")
    private LocalDate expenseDate;

    @NotBlank(message = "Payment method cannot be null or empty")
    private String paymentMethod;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "ExpenseDTO{" +
                "userId=" + userId +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", expenseDate=" + expenseDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
