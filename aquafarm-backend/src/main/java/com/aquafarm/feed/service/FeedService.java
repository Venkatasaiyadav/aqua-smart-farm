// src/main/java/com/aquafarm/feed/service/FeedService.java
package com.aquafarm.feed.service;

import com.aquafarm.common.exception.ResourceNotFoundException;
import com.aquafarm.feed.dto.CreateFeedRequest;
import com.aquafarm.feed.dto.FeedEntryResponse;
import com.aquafarm.feed.entity.FeedEntry;
import com.aquafarm.feed.repository.FeedEntryRepository;
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
public class FeedService {

    private final FeedEntryRepository feedEntryRepository;
    private final PondRepository pondRepository;

    @Transactional
    public FeedEntryResponse addFeedEntry(
            CreateFeedRequest request) {

        Pond pond = pondRepository.findById(request.getPondId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Pond", request.getPondId()));

        FeedEntry entry = FeedEntry.builder()
                .feedDate(request.getFeedDate() != null
                    ? request.getFeedDate() : LocalDate.now())
                .feedTime(request.getFeedTime())
                .feedStage(request.getFeedStage())
                .quantityKg(request.getQuantityKg())
                .brand(request.getBrand() != null
                    ? request.getBrand() : "AVANTI")
                .remarks(request.getRemarks())
                .pond(pond)
                .build();

        entry = feedEntryRepository.save(entry);
        return mapToResponse(entry);
    }

    public List<FeedEntryResponse> getFeedByPond(Long pondId) {
        return feedEntryRepository
                .findByPondIdOrderByFeedDateDesc(pondId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<FeedEntryResponse> getFeedByPondAndDate(
            Long pondId, LocalDate date) {
        return feedEntryRepository
                .findByPondIdAndFeedDate(pondId, date)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<FeedEntryResponse> getTodayFeedForFarm(
            Long farmId) {
        return feedEntryRepository
                .findByFarmIdAndDate(farmId, LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Double getTotalFeedForPond(Long pondId) {
        return feedEntryRepository.getTotalFeedByPondId(pondId);
    }

    private FeedEntryResponse mapToResponse(FeedEntry entry) {
        return FeedEntryResponse.builder()
                .id(entry.getId())
                .pondId(entry.getPond().getId())
                .pondName(entry.getPond().getPondName())
                .feedDate(entry.getFeedDate())
                .feedTime(entry.getFeedTime().name())
                .feedStage(entry.getFeedStage().name())
                .quantityKg(entry.getQuantityKg())
                .brand(entry.getBrand())
                .remarks(entry.getRemarks())
                .createdAt(entry.getCreatedAt())
                .build();
    }
}