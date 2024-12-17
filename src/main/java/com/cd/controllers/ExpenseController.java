package com.cd.controllers;

import com.cd.commons.exceptions.ExpenseIDMismatchException;
import com.cd.models.Expense;
import com.cd.models.ExpenseDTO;
import com.cd.models.MonthlyExpenseReportDTO;
import com.cd.models.User;
import com.cd.services.ExpenseService;
import com.cd.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense newExpense = expenseService.createExpense(expense);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newExpense.getId()).toUri();

        return ResponseEntity.created(location).body(newExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(Authentication authentication) {
        User user = userService.findUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        return ResponseEntity.ok(expenseService.getAllExpensesByUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable int id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable int id, @Valid @RequestBody Expense expense) {
        if (id != expense.getId()) {
            throw new ExpenseIDMismatchException("Expense ID mismatch!");
        }
        Expense updatedExpense = expenseService.updateExpense(expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable int id) {
        if (expenseService.deleteExpense(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/monthly/report")
    public ResponseEntity<List<MonthlyExpenseReportDTO>> getMonthlyReport(Authentication authentication) {
        User user = userService.findUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        return ResponseEntity.ok(expenseService.getMonthlyExpenseReport(user));
    }
}
