package com.example.expensetracker.controller;

import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/expenses")
@Validated
public class ExpenseController {

    private final ExpenseService expenseService;

    private static final Set<String> ALLOWED_SORT_BY = Set.of("date", "amount", "category");
    private static final Set<String> ALLOWED_ORDER = Set.of("asc", "desc");

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        if (!ALLOWED_SORT_BY.contains(sortBy.toLowerCase())) {
            return ResponseEntity.badRequest().build();
        }
        if (!ALLOWED_ORDER.contains(order.toLowerCase())) {
            return ResponseEntity.badRequest().build();
        }

        Category categoryEnum = null;
        if (category != null && !category.isBlank()) {
            try {
                categoryEnum = Category.valueOf(category.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().build();
            }
        }

        List<Expense> expenses = expenseService.getAllExpenses(
                startDate,
                endDate,
                categoryEnum,
                minAmount,
                maxAmount,
                sortBy.toLowerCase(),
                order.toLowerCase()
        );
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense created = expenseService.createExpense(expense);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody Expense expense) {

        Expense updated = expenseService.updateExpense(id, expense);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/by-category")
    public ResponseEntity<Map<String, Double>> getSummaryByCategory() {
        Map<String, Double> summary = expenseService.getSummaryByCategory();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/summary/monthly")
    public ResponseEntity<Map<String, Double>> getMonthlySummary() {
        Map<String, Double> summary = expenseService.getMonthlySummary();
        return ResponseEntity.ok(summary);
    }
}
