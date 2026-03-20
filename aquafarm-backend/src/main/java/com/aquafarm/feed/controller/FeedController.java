// src/main/java/com/aquafarm/feed/controller/FeedController.java
package com.aquafarm.feed.controller;

import com.aquafarm.common.dto.ApiResponse;
import com.aquafarm.feed.dto.CreateFeedRequest;
import com.aquafarm.feed.dto.FeedEntryResponse;
import com.aquafarm.feed.service.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // POST /api/feed — Add daily feed entry
    @PostMapping
    public ResponseEntity<ApiResponse<FeedEntryResponse>>
            addFeed(
                @Valid @RequestBody CreateFeedRequest request) {

        FeedEntryResponse response =
            feedService.addFeedEntry(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                    "Feed entry saved! 🍤", response));
    }

    // GET /api/feed/pond/1 — All feed for a pond
    @GetMapping("/pond/{pondId}")
    public ResponseEntity<ApiResponse<List<FeedEntryResponse>>>
            getFeedByPond(@PathVariable Long pondId) {

        List<FeedEntryResponse> feeds =
            feedService.getFeedByPond(pondId);

        return ResponseEntity.ok(
            ApiResponse.success(feeds));
    }

    // GET /api/feed/pond/1/date?date=2026-03-15
    @GetMapping("/pond/{pondId}/date")
    public ResponseEntity<ApiResponse<List<FeedEntryResponse>>>
            getFeedByDate(
                @PathVariable Long pondId,
                @RequestParam
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate date) {

        List<FeedEntryResponse> feeds =
            feedService.getFeedByPondAndDate(pondId, date);

        return ResponseEntity.ok(
            ApiResponse.success(feeds));
    }

    // GET /api/feed/today?farmId=1
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<FeedEntryResponse>>>
            getTodayFeed(@RequestParam Long farmId) {

        List<FeedEntryResponse> feeds =
            feedService.getTodayFeedForFarm(farmId);

        return ResponseEntity.ok(
            ApiResponse.success(feeds));
    }
}