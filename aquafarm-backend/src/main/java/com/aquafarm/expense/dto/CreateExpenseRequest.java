// src/main/java/com/aquafarm/expense/dto/CreateExpenseRequest.java
package com.aquafarm.expense.dto;

import com.aquafarm.common.enums.ExpenseCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateExpenseRequest {

    private LocalDate expenseDate;

    @NotNull(message = "Category is required")
    private ExpenseCategory category;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    private String description;
    private Long pondId;  // null = farm-level expense
}