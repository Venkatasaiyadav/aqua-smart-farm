// src/main/java/com/aquafarm/expense/entity/Expense.java
package com.aquafarm.expense.entity;

import com.aquafarm.common.enums.ExpenseCategory;
import com.aquafarm.farm.entity.Farm;
import com.aquafarm.pond.entity.Pond;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExpenseCategory category;

    @Column(nullable = false)
    private Double amount;

    @Column(length = 255)
    private String description;

    // Nullable — some expenses are farm-level, not pond-specific
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pond_id")
    private Pond pond;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}