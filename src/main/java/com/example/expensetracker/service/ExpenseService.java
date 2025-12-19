package com.example.expensetracker.service;

import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseService {

    Expense createExpense(Expense expense);
    Expense getExpenseById(Long id);
    Expense updateExpense(Long id, Expense expense);
    void deleteExpense(Long id);


    List<Expense> getAllExpenses(
            LocalDate startDate,
            LocalDate endDate,
            Category category,
            Double minAmount,
            Double maxAmount,
            String sortBy,
            String order
    );


    Map<String, Double> getSummaryByCategory();
    Map<String, Double> getMonthlySummary();
}
