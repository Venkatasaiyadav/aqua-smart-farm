// src/main/java/com/aquafarm/expense/repository/ExpenseRepository.java
package com.aquafarm.expense.repository;

import com.aquafarm.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByFarmIdOrderByExpenseDateDesc(Long farmId);

    List<Expense> findByPondIdOrderByExpenseDateDesc(Long pondId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
           "WHERE e.farm.id = :farmId")
    Double getTotalExpenseByFarmId(@Param("farmId") Long farmId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
           "WHERE e.pond.id = :pondId")
    Double getTotalExpenseByPondId(@Param("pondId") Long pondId);
}