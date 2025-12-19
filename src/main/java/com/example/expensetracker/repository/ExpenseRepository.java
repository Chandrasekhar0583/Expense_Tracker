package com.example.expensetracker.repository;

import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    List<Expense> findByCategory(Category category);

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);


    @Query("""
        SELECT e.category, SUM(e.amount)
        FROM Expense e
        GROUP BY e.category
    """)
    List<Object[]> getTotalByCategory();


    @Query("""
        SELECT MONTH(e.date), SUM(e.amount)
        FROM Expense e
        WHERE YEAR(e.date) = YEAR(CURRENT_DATE)
        GROUP BY MONTH(e.date)
        ORDER BY MONTH(e.date)
    """)
    List<Object[]> getMonthlySummary();
}
