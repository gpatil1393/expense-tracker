package com.cd.repositories;

import com.cd.models.Expense;
import com.cd.models.MonthlyExpenseReportDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    public List<Expense> findAllByUserId(int userId, Sort sort);

    @Query("SELECT new com.cd.models.MonthlyExpenseReportDTO " +
            "(EXTRACT(YEAR FROM e.expenseDate), EXTRACT(MONTH FROM e.expenseDate), SUM(e.amount)) " +
            "FROM Expense e WHERE e.userId = :userId " +
            "GROUP BY EXTRACT(YEAR FROM e.expenseDate), EXTRACT(MONTH FROM e.expenseDate) " +
            "ORDER BY EXTRACT(YEAR FROM e.expenseDate) DESC, EXTRACT(MONTH FROM e.expenseDate) DESC")
    public List<MonthlyExpenseReportDTO> getMonthlyExpenseReport(int userId);

    List<Expense> findAllByUserIdAndCategoryIdAndExpenseDateIsBefore(@NotNull(message = "User cannot be null") int userId, @NotNull(message = "Category Cannot be null") int categoryId, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateBefore);

    List<Expense> findAllByUserIdAndCategoryIdAndExpenseDateIsAfter(@NotNull(message = "User cannot be null") int userId, @NotNull(message = "Category Cannot be null") int categoryId, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateAfter);

    List<Expense> findAllByUserIdAndExpenseDateIsAfter(@NotNull(message = "User cannot be null") int userId, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateAfter);

    List<Expense> findAllByUserIdAndExpenseDateBetween(@NotNull(message = "User cannot be null") int userId, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateAfter, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateBefore);

    List<Expense> findAllByUserIdAndCategoryIdAndExpenseDateBetween(@NotNull(message = "User cannot be null") int userId, @NotNull(message = "Category Cannot be null") int categoryId, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateAfter, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateBefore);

    List<Expense> findAllByUserIdAndExpenseDateIsBefore(@NotNull(message = "User cannot be null") int userId, @NotNull(message = "Expense date cannot be null or empty") LocalDate expenseDateBefore);

}
