package com.cd.controllers;

import com.cd.commons.exceptions.ExpenseIDMismatchException;
import com.cd.models.Expense;
import com.cd.models.MonthlyExpenseReportDTO;
import com.cd.models.User;
import com.cd.services.ExpenseService;
import com.cd.services.UserService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ExpenseController expenseController;

    private User user;
    private Expense expense;
    private int expenseId;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("gaurav.patil@nitorinfotech.com");
        user.setUsername("gpatil");
        user.setFirstName("Gaurav");
        user.setLastName("Patil");

        authentication = new UsernamePasswordAuthenticationToken(user, "Welcome123$");

        expense = new Expense();
        expenseId = 1;
        expense.setId(expenseId);
        expense.setExpenseDate(LocalDate.now());
        expense.setAmount(500);
        expense.setDescription("Lunch");
        expense.setCategoryId(1);
        expense.setPaymentMethod("UPI");
        expense.setUserId(1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createExpense() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(expenseService.createExpense(any(Expense.class))).thenReturn(expense);

        ResponseEntity<Expense> response = expenseController.createExpense(expense);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expense, response.getBody());
        assertEquals(expenseId, Objects.requireNonNull(response.getBody()).getId());

        verify(expenseService).createExpense(expense);
    }

    @Test
    @WithMockUser(username = "gpatil")
    void getExpenses() {
        when(userService.findUserByUsername("gpatil")).thenReturn(user);
        List<Expense> expenses = Collections.singletonList(expense);
        when(expenseService.getAllExpensesByUser(user)).thenReturn(expenses);
        // when(expenseService.getAllExpensesByUser(0, null, null, user)).thenReturn(expenses);


        ResponseEntity<List<Expense>> response = expenseController.getExpenses(0, null, null, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(expense, response.getBody().get(0));

        verify(expenseService).getAllExpensesByUser(user);
    }

    @Test
    void getExpenseById() {
        // when(userService.findUserByUsername("gpatil")).thenReturn(user);
        when(expenseService.getExpenseById(expenseId)).thenReturn(expense);

        ResponseEntity<Expense> response = expenseController.getExpenseById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expense.getId(), Objects.requireNonNull(response.getBody()).getId());

        verify(expenseService).getExpenseById(expenseId);
    }

    @Test
    void updateExpense() {
        when(expenseService.updateExpense(expense)).thenReturn(expense);

        ResponseEntity<Expense> response = expenseController.updateExpense(expenseId, expense);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expense.getId(), Objects.requireNonNull(response.getBody()).getId());

        verify(expenseService).updateExpense(expense);
    }

    @Test
    void updateExpenseIDMismatchTest() {
        assertThrows(ExpenseIDMismatchException.class, () -> expenseController.updateExpense(10, expense));
    }

    @Test
    void deleteExpense() {
        when(expenseService.deleteExpense(expenseId)).thenReturn(true);
        ResponseEntity<Expense> response = expenseController.deleteExpense(expenseId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getMonthlyReport() {
        when(userService.findUserByUsername("gpatil")).thenReturn(user);
        MonthlyExpenseReportDTO reportDTO = new MonthlyExpenseReportDTO(12, 2024, 1000);
        when(expenseService.getMonthlyExpenseReport(user)).thenReturn(Collections.singletonList(reportDTO));

        ResponseEntity<List<MonthlyExpenseReportDTO>> response = expenseController.getMonthlyReport(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(reportDTO, response.getBody().get(0));
        verify(expenseService).getMonthlyExpenseReport(user);
    }
}