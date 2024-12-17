package com.cd.repositories;

import com.cd.models.Expense;
import com.cd.models.MonthlyExpenseReportDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    public List<Expense> findAllByUserId(int userId, Sort sort);

    @Query("SELECT new com.cd.models.MonthlyExpenseReportDTO " +
            "(EXTRACT(YEAR FROM e.expenseDate), EXTRACT(MONTH FROM e.expenseDate), SUM(e.amount)) " +
            "FROM Expense e WHERE e.userId = :userId " +
            "GROUP BY EXTRACT(YEAR FROM e.expenseDate), EXTRACT(MONTH FROM e.expenseDate) " +
            "ORDER BY EXTRACT(YEAR FROM e.expenseDate) DESC, EXTRACT(MONTH FROM e.expenseDate) DESC")
    public List<MonthlyExpenseReportDTO> getMonthlyExpenseReport(int userId);
}
