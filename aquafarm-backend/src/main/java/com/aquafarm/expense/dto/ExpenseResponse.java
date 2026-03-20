// src/main/java/com/aquafarm/expense/dto/ExpenseResponse.java
package com.aquafarm.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private LocalDate expenseDate;
    private String category;
    private Double amount;
    private String description;
    private Long pondId;
    private String pondName;
}