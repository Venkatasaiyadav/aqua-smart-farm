// src/main/java/com/aquafarm/growth/service/GrowthService.java
package com.aquafarm.growth.service;

import com.aquafarm.common.exception.ResourceNotFoundException;
import com.aquafarm.growth.dto.CreateGrowthRequest;
import com.aquafarm.growth.dto.GrowthSampleResponse;
import com.aquafarm.growth.entity.GrowthSample;
import com.aquafarm.growth.repository.GrowthSampleRepository;
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
public class GrowthService {

    private final GrowthSampleRepository growthSampleRepository;
    private final PondRepository pondRepository;

    @Transactional
    public GrowthSampleResponse addGrowthSample(
            CreateGrowthRequest request) {

        Pond pond = pondRepository.findById(request.getPondId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Pond", request.getPondId()));

        // Calculate biomass
        double survivalRate = request.getSurvivalRate() != null
            ? request.getSurvivalRate() : 85.0;
        int aliveCount = (int) (pond.getSeedCount()
            * (survivalRate / 100.0));
        double biomass = aliveCount
            * request.getAvgWeightGrams() / 1000.0;

        GrowthSample sample = GrowthSample.builder()
                .sampleDate(request.getSampleDate() != null
                    ? request.getSampleDate() : LocalDate.now())
                .avgWeightGrams(request.getAvgWeightGrams())
                .sampleCount(request.getSampleCount() != null
                    ? request.getSampleCount() : 50)
                .survivalRate(survivalRate)
                .estimatedBiomass(biomass)
                .healthStatus(request.getHealthStatus() != null
                    ? request.getHealthStatus() : "HEALTHY")
                .remarks(request.getRemarks())
                .pond(pond)
                .build();

        sample = growthSampleRepository.save(sample);
        return mapToResponse(sample, pond);
    }

    public List<GrowthSampleResponse> getGrowthByPond(
            Long pondId) {

        Pond pond = pondRepository.findById(pondId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Pond", pondId));

        return growthSampleRepository
                .findByPondIdOrderBySampleDateDesc(pondId)
                .stream()
                .map(s -> mapToResponse(s, pond))
                .collect(Collectors.toList());
    }

    public GrowthSampleResponse getLatestGrowth(Long pondId) {
        Pond pond = pondRepository.findById(pondId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Pond", pondId));

        GrowthSample sample = growthSampleRepository
                .findTopByPondIdOrderBySampleDateDesc(pondId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No growth samples for pond " + pondId));

        return mapToResponse(sample, pond);
    }

    private GrowthSampleResponse mapToResponse(
            GrowthSample sample, Pond pond) {

        int aliveCount = (int) (pond.getSeedCount()
            * (sample.getSurvivalRate() / 100.0));

        /*
         📚 LEARN: Feed Recommendation Formula

         Feeding % depends on culture day:
         Day  1-30:  8-10% of biomass (starter)
         Day 31-60:  4-6% of biomass (grower)
         Day 61-90:  2.5-3.5% of biomass (grow-out)
         Day 91-120: 1.5-2.5% of biomass (pre-harvest)

         Daily Feed = Biomass × Feeding %
        */
        long cultureDay = pond.getCultureDay();
        double feedingPercent;
        if (cultureDay <= 30) feedingPercent = 0.08;
        else if (cultureDay <= 60) feedingPercent = 0.05;
        else if (cultureDay <= 90) feedingPercent = 0.03;
        else feedingPercent = 0.02;

        double recommendedFeed =
            sample.getEstimatedBiomass() * feedingPercent;

        return GrowthSampleResponse.builder()
                .id(sample.getId())
                .pondId(pond.getId())
                .pondName(pond.getPondName())
                .sampleDate(sample.getSampleDate())
                .avgWeightGrams(sample.getAvgWeightGrams())
                .sampleCount(sample.getSampleCount())
                .survivalRate(sample.getSurvivalRate())
                .estimatedBiomass(sample.getEstimatedBiomass())
                .healthStatus(sample.getHealthStatus())
                .remarks(sample.getRemarks())
                .estimatedAliveCount(aliveCount)
                .recommendedDailyFeedKg(
                    Math.round(recommendedFeed * 100.0) / 100.0)
                .build();
    }
}