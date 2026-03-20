// src/main/java/com/aquafarm/expense/service/ExpenseService.java
package com.aquafarm.expense.service;

import com.aquafarm.common.exception.ResourceNotFoundException;
import com.aquafarm.expense.dto.CreateExpenseRequest;
import com.aquafarm.expense.dto.ExpenseResponse;
import com.aquafarm.expense.entity.Expense;
import com.aquafarm.expense.repository.ExpenseRepository;
import com.aquafarm.farm.entity.Farm;
import com.aquafarm.farm.repository.FarmRepository;
import com.aquafarm.pond.entity.Pond;
import com.aquafarm.pond.repository.PondRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final FarmRepository farmRepository;
    private final PondRepository pondRepository;

    @Transactional
    public ExpenseResponse addExpense(Long farmId,
                                       CreateExpenseRequest request) {

        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Farm", farmId));

        Pond pond = null;
        if (request.getPondId() != null) {
            pond = pondRepository.findById(request.getPondId())
                    .orElseThrow(() ->
                        new ResourceNotFoundException(
                            "Pond", request.getPondId()));
        }

        Expense expense = Expense.builder()
                .expenseDate(request.getExpenseDate() != null
                    ? request.getExpenseDate() : LocalDate.now())
                .category(request.getCategory())
                .amount(request.getAmount())
                .description(request.getDescription())
                .pond(pond)
                .farm(farm)
                .build();

        expense = expenseRepository.save(expense);
        return mapToResponse(expense);
    }

    public List<ExpenseResponse> getExpensesByFarm(Long farmId) {
        return expenseRepository
                .findByFarmIdOrderByExpenseDateDesc(farmId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Double getTotalExpense(Long farmId) {
        return expenseRepository
                .getTotalExpenseByFarmId(farmId);
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .expenseDate(expense.getExpenseDate())
                .category(expense.getCategory().name())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .pondId(expense.getPond() != null
                    ? expense.getPond().getId() : null)
                .pondName(expense.getPond() != null
                    ? expense.getPond().getPondName() : "Farm Level")
                .build();
    }
}