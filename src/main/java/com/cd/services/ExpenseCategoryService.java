package com.cd.services;

import com.cd.commons.exceptions.ExpenseCategoryNotFoundException;
import com.cd.models.ExpenseCategory;
import com.cd.repositories.ExpenseCategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ExpenseCategory createExpenseCategory(ExpenseCategory expenseCategory) {
        return expenseCategoryRepository.save(expenseCategory);
    }

    public ExpenseCategory getExpenseCategoryById(int id) {
        return expenseCategoryRepository.findById(id).orElseThrow(() -> new ExpenseCategoryNotFoundException("Expense category record not found"));
    }

    public List<ExpenseCategory> getAllExpenseCategories() {
        return expenseCategoryRepository.findAll(Sort.by("categoryName").ascending());
    }

    public ExpenseCategory findExpenseCategoryById(int id) {
        return expenseCategoryRepository.findById(id).orElseThrow(() -> new ExpenseCategoryNotFoundException("Expense category record not found"));
    }
}
