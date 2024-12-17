package com.cd;

import com.cd.models.ExpenseCategory;
import com.cd.repositories.ExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ExpenseTrackerApplication implements CommandLineRunner {

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> categories = Arrays.asList("Food", "Entertainment", "Transport", "Utilities", "Miscellaneous");
        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll();
        if (expenseCategoryList.isEmpty()) {
            categories.forEach(category -> {
                expenseCategoryRepository.save(new ExpenseCategory(category));
            });
        }
    }
}
