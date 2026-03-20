// src/main/java/com/aquafarm/dashboard/service/DashboardService.java
package com.aquafarm.dashboard.service;

import com.aquafarm.common.exception.ResourceNotFoundException;
import com.aquafarm.dashboard.dto.DashboardResponse;
import com.aquafarm.expense.repository.ExpenseRepository;
import com.aquafarm.farm.entity.Farm;
import com.aquafarm.farm.repository.FarmRepository;
import com.aquafarm.feed.repository.FeedEntryRepository;
import com.aquafarm.pond.dto.PondResponse;
import com.aquafarm.pond.service.PondService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FarmRepository farmRepository;
    private final PondService pondService;
    private final FeedEntryRepository feedEntryRepository;
    private final ExpenseRepository expenseRepository;

    private static final double MARKET_PRICE_PER_KG = 300.0;

    public DashboardResponse getDashboard(Long farmId) {

        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Farm", farmId));

        // Get all ponds with details
        List<PondResponse> ponds =
            pondService.getAllPonds(farmId);

        // Calculate today's total feed
        double todayFeed = ponds.stream()
                .mapToDouble(p -> p.getTodayFeedKg() != null
                    ? p.getTodayFeedKg() : 0.0)
                .sum();

        // Total feed consumed
        double totalFeed = feedEntryRepository
            .getTotalFeedByFarmId(farmId);

        // Total expenses
        double totalExpense = expenseRepository
            .getTotalExpenseByFarmId(farmId);

        // Estimated revenue (total biomass × market price)
        double totalBiomass = ponds.stream()
                .mapToDouble(p -> p.getBiomass() != null
                    ? p.getBiomass() : 0.0)
                .sum();
        double estimatedRevenue = totalBiomass
            * MARKET_PRICE_PER_KG;

        // Generate alerts
        List<String> alerts = generateAlerts(ponds);

        return DashboardResponse.builder()
                .farmName(farm.getFarmName())
                .ownerName(farm.getOwner().getFullName())
                .activePondCount(ponds.size())
                .todayTotalFeedKg(todayFeed)
                .totalFeedConsumedKg(totalFeed)
                .totalExpense(totalExpense)
                .estimatedRevenue(estimatedRevenue)
                .ponds(ponds)
                .alerts(alerts)
                .build();
    }

    private List<String> generateAlerts(
            List<PondResponse> ponds) {

        List<String> alerts = new ArrayList<>();

        for (PondResponse pond : ponds) {
            // Alert if no feed entered today
            if (pond.getTodayFeedKg() == null
                    || pond.getTodayFeedKg() == 0) {
                alerts.add("⚠️ No feed entered today for "
                    + pond.getPondName());
            }

            // Alert if culture day > 110
            if (pond.getCultureDay() > 110) {
                alerts.add("🦐 " + pond.getPondName()
                    + " is ready for harvest! Day "
                    + pond.getCultureDay());
            }

            // Alert if no growth sample exists
            if (pond.getLatestWeight() == null) {
                alerts.add("📊 Please add growth sample for "
                    + pond.getPondName());
            }
        }

        return alerts;
    }
}