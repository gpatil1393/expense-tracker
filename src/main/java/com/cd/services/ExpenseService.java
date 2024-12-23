package com.cd.services;

import com.cd.commons.exceptions.ExpenseRecordNotFoundException;
import com.cd.models.*;
import com.cd.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final UserService userService;
    private final ExpenseCategoryService expenseCategoryService;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, UserService userService, ExpenseCategoryService expenseCategoryService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
        this.expenseCategoryService = expenseCategoryService;
    }

    public Expense createExpense(Expense expense) {
        /*User user = userService.findUserById(expense.getUserId());
        ExpenseCategory expenseCategory = expenseCategoryService.findExpenseCategoryById(expense.getCategoryId());
        Expense expenseRecord = new Expense(user, expenseCategory, expense.getAmount(), expense.getDescription(), expense.getExpenseDate(), expense.getPaymentMethod());
        expenseRecord.setCreatedAt(LocalDateTime.now());*/
        // System.out.println("expenseRecord: " + expenseRecord.toString());
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Expense expense) {
        getExpenseById(expense.getId());
        return expenseRepository.save(expense);
    }

    public Expense getExpenseById(int id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseRecordNotFoundException("Expense record not found!"));
    }

    public List<Expense> getAllExpensesByUser(User user) {
        return expenseRepository.findAllByUserId(user.getId(), Sort.by("expenseDate").descending());
    }

    public List<Expense> getAllExpensesByUser(int categoryId, LocalDate startDate, LocalDate endDate, User user) {
        if (categoryId != 0 && startDate != null && endDate != null) {
            return expenseRepository.findAllByUserIdAndCategoryIdAndExpenseDateBetween(user.getId(), categoryId, startDate, endDate);
        } else if (categoryId == 0 && startDate != null && endDate != null) {
            return expenseRepository.findAllByUserIdAndExpenseDateBetween(user.getId(), startDate, endDate);
        } else if (categoryId == 0 && startDate == null && endDate != null) {
            return expenseRepository.findAllByUserIdAndExpenseDateIsBefore(user.getId(), endDate);
        } else if (categoryId == 0 && startDate != null) {
            return expenseRepository.findAllByUserIdAndExpenseDateIsAfter(user.getId(), startDate);
        } else if (categoryId != 0 && endDate == null && startDate != null) {
            return expenseRepository.findAllByUserIdAndCategoryIdAndExpenseDateIsAfter(user.getId(), categoryId, startDate);
        } else if (categoryId != 0 && endDate != null) {
            return expenseRepository.findAllByUserIdAndCategoryIdAndExpenseDateIsBefore(user.getId(), categoryId, endDate);
        } else if (categoryId != 0) {
            return expenseRepository.findAllByUserIdAndCategoryId(user.getId(), categoryId);
        }
        return expenseRepository.findAllByUserId(user.getId(), Sort.by("expenseDate").descending());
    }

    public boolean deleteExpense(int id) {
        getExpenseById(id);
        expenseRepository.deleteById(id);
        return true;
    }

    public List<MonthlyExpenseReportDTO> getMonthlyExpenseReport(User user) {
        return expenseRepository.getMonthlyExpenseReport(user.getId());
    }
}
