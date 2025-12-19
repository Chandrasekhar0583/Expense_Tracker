package com.example.expensetracker.service;

import com.example.expensetracker.exception.ResourceNotFoundException;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existing = getExpenseById(id);
        existing.setAmount(expense.getAmount());
        existing.setCategory(expense.getCategory());
        existing.setDate(expense.getDate());
        existing.setDescription(expense.getDescription());
        return expenseRepository.save(existing);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }



    @Override
    public List<Expense> getAllExpenses(
            LocalDate startDate,
            LocalDate endDate,
            Category category,
            Double minAmount,
            Double maxAmount,
            String sortBy,
            String order
    ) {
        Sort sort = Sort.by(
                "amount".equalsIgnoreCase(sortBy) ? "amount" : "date"
        );

        if ("desc".equalsIgnoreCase(order)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        List<Expense> expenses = expenseRepository.findAll(sort);


        return expenses.stream()
                .filter(e -> startDate == null || !e.getDate().isBefore(startDate))
                .filter(e -> endDate == null || !e.getDate().isAfter(endDate))
                .filter(e -> category == null || e.getCategory() == category)
                .filter(e -> minAmount == null || e.getAmount() >= minAmount)
                .filter(e -> maxAmount == null || e.getAmount() <= maxAmount)
                .toList();
    }



    @Override
    public Map<String, Double> getSummaryByCategory() {
        Map<String, Double> result = new HashMap<>();
        for (Object[] row : expenseRepository.getTotalByCategory()) {
            result.put(row[0].toString(), (Double) row[1]);
        }
        return result;
    }

    @Override
    public Map<String, Double> getMonthlySummary() {
        Map<String, Double> result = new LinkedHashMap<>();
        for (Object[] row : expenseRepository.getMonthlySummary()) {
            int monthNumber = ((Number) row[0]).intValue();
            String monthName = Month.of(monthNumber).name();
            result.put(monthName, (Double) row[1]);
        }
        return result;
    }
}
