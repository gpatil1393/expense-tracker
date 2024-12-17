package com.cd.controllers;

import com.cd.commons.exceptions.ExpenseCategoryNotFoundException;
import com.cd.models.ExpenseCategory;
import com.cd.services.ExpenseCategoryService;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseCategoryControllerTest {

    @Mock
    private ExpenseCategoryService expenseCategoryService;

    @InjectMocks
    private ExpenseCategoryController expenseCategoryController;

    private ExpenseCategory expenseCategory;
    private int expenseCategoryId;

    @BeforeEach
    void setUp() {
        expenseCategoryId = 1;
        expenseCategory = new ExpenseCategory();
        expenseCategory.setId(expenseCategoryId);
        expenseCategory.setCategoryName("Food");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createExpenseCategory() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(expenseCategoryService.createExpenseCategory(expenseCategory)).thenReturn(expenseCategory);

        ResponseEntity<ExpenseCategory> response = expenseCategoryController.createExpenseCategory(expenseCategory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expenseCategory, response.getBody());
        assertEquals(expenseCategoryId, response.getBody().getId());

        verify(expenseCategoryService).createExpenseCategory(expenseCategory);
    }

    @Test
    void getExpenseCategoryById() {
        when(expenseCategoryService.getExpenseCategoryById(expenseCategoryId)).thenReturn(expenseCategory);

        ResponseEntity<ExpenseCategory> response = expenseCategoryController.getExpenseCategoryById(expenseCategoryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expenseCategory, response.getBody());
        assertEquals(expenseCategoryId, response.getBody().getId());

        verify(expenseCategoryService).getExpenseCategoryById(expenseCategoryId);
    }

    @Test
    void getExpenseCategoryByIdNotFound() {
        when(expenseCategoryService.getExpenseCategoryById(10)).thenThrow(ExpenseCategoryNotFoundException.class);
        assertThrows(ExpenseCategoryNotFoundException.class, () -> expenseCategoryController.getExpenseCategoryById(10));
    }

    @Test
    void getAllExpenseCategories() {
        when(expenseCategoryService.getAllExpenseCategories()).thenReturn(Collections.singletonList(expenseCategory));

        ResponseEntity<List<ExpenseCategory>> response = expenseCategoryController.getAllExpenseCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(expenseCategory, response.getBody().get(0));

        verify(expenseCategoryService).getAllExpenseCategories();
    }
}