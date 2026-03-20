// src/main/java/com/aquafarm/expense/controller/ExpenseController.java
package com.aquafarm.expense.controller;

import com.aquafarm.common.dto.ApiResponse;
import com.aquafarm.expense.dto.CreateExpenseRequest;
import com.aquafarm.expense.dto.ExpenseResponse;
import com.aquafarm.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    // POST /api/expenses?farmId=1
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponse>>
            addExpense(
                @RequestParam Long farmId,
                @Valid @RequestBody CreateExpenseRequest request) {

        ExpenseResponse response =
            expenseService.addExpense(farmId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                    "Expense recorded! 💰", response));
    }

    // GET /api/expenses?farmId=1
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>>
            getExpenses(@RequestParam Long farmId) {

        List<ExpenseResponse> expenses =
            expenseService.getExpensesByFarm(farmId);

        return ResponseEntity.ok(
            ApiResponse.success(expenses));
    }
}