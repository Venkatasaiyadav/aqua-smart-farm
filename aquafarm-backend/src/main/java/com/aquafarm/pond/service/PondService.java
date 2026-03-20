// src/main/java/com/aquafarm/pond/service/PondService.java
package com.aquafarm.pond.service;

import com.aquafarm.common.exception.ResourceNotFoundException;
import com.aquafarm.farm.entity.Farm;
import com.aquafarm.farm.repository.FarmRepository;
import com.aquafarm.feed.repository.FeedEntryRepository;
import com.aquafarm.growth.entity.GrowthSample;
import com.aquafarm.growth.repository.GrowthSampleRepository;
import com.aquafarm.pond.dto.CreatePondRequest;
import com.aquafarm.pond.dto.PondResponse;
import com.aquafarm.pond.entity.Pond;
import com.aquafarm.pond.repository.PondRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PondService {

    private final PondRepository pondRepository;
    private final FarmRepository farmRepository;
    private final FeedEntryRepository feedEntryRepository;
    private final GrowthSampleRepository growthSampleRepository;

    private static final double FEED_PRICE_PER_KG = 320.0;

    @Transactional
    public PondResponse createPond(Long farmId,
                                    CreatePondRequest request) {

        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Farm", farmId));

        Pond pond = Pond.builder()
                .pondName(request.getPondName())
                .sizeAcre(request.getSizeAcre())
                .stockingDate(request.getStockingDate() != null
                    ? request.getStockingDate() : LocalDate.now())
                .seedCount(request.getSeedCount() != null
                    ? request.getSeedCount() : 200000)
                .prawnType(request.getPrawnType() != null
                    ? request.getPrawnType() : "VANNAMEI")
                .farm(farm)
                .build();

        pond = pondRepository.save(pond);
        return mapToResponse(pond);
    }

    public List<PondResponse> getAllPonds(Long farmId) {
        List<Pond> ponds = pondRepository.findByFarmId(farmId);
        return ponds.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PondResponse getPondById(Long pondId) {
        Pond pond = pondRepository.findById(pondId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Pond", pondId));
        return mapToResponse(pond);
    }

    /*
     📚 LEARN: Mapping Entity → DTO
     
     This method converts database Pond object
     into API-friendly PondResponse.
     
     It also calculates:
     - Culture day
     - Growth stage
     - Latest weight
     - Total feed
     - Feed cost
    */
    private PondResponse mapToResponse(Pond pond) {

        // Get latest growth sample
        Optional<GrowthSample> latestSample =
            growthSampleRepository
                .findTopByPondIdOrderBySampleDateDesc(pond.getId());

        // Get feed totals
        Double totalFeed = feedEntryRepository
            .getTotalFeedByPondId(pond.getId());
        Double todayFeed = feedEntryRepository
            .getTotalFeedByPondIdAndDate(
                pond.getId(), LocalDate.now());

        PondResponse response = PondResponse.builder()
                .id(pond.getId())
                .pondName(pond.getPondName())
                .sizeAcre(pond.getSizeAcre())
                .stockingDate(pond.getStockingDate())
                .seedCount(pond.getSeedCount())
                .prawnType(pond.getPrawnType())
                .status(pond.getStatus().name())
                .cultureDay(pond.getCultureDay())
                .growthStage(pond.getGrowthStage())
                .totalFeedKg(totalFeed)
                .todayFeedKg(todayFeed)
                .totalFeedCost(totalFeed * FEED_PRICE_PER_KG)
                .build();

        // Add growth data if available
        latestSample.ifPresent(sample -> {
            response.setLatestWeight(sample.getAvgWeightGrams());
            response.setSurvivalRate(sample.getSurvivalRate());
            response.setBiomass(sample.getEstimatedBiomass());
        });

        return response;
    }
}