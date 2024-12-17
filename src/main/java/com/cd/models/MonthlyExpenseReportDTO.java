package com.cd.models;

public class MonthlyExpenseReportDTO {
    private int month;
    private int year;
    private double amount;

    public MonthlyExpenseReportDTO(int month, int year, double amount) {
        this.month = month;
        this.year = year;
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
