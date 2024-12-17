package com.cd.controllers.advices;

import com.cd.commons.ErrorResponse;
import com.cd.commons.exceptions.ExpenseIDMismatchException;
import com.cd.commons.exceptions.ExpenseRecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExpenseControllerAdvice {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExpenseRecordNotFound(ExpenseRecordNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("Expense - 404");
        errorResponse.setErrorMessage("Expense record not found");
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidInputException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("Expense - 400");
        errorResponse.setErrorMessage("Expense details are not valid");
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setFieldErrors(e.getFieldErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExpenseIDMismatchException(ExpenseIDMismatchException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("Expense - 400");
        errorResponse.setErrorMessage("Expense ID mismatch");
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleForbiddenException(AuthorizationDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("Expense - 401");
        errorResponse.setErrorMessage("Unauthorized");
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("Expense - 500");
        errorResponse.setErrorMessage("A server exception occurred!");
        errorResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
