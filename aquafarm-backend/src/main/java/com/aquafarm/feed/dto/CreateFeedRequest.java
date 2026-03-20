// src/main/java/com/aquafarm/feed/dto/CreateFeedRequest.java
package com.aquafarm.feed.dto;

import com.aquafarm.common.enums.FeedStage;
import com.aquafarm.common.enums.FeedTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateFeedRequest {

    @NotNull(message = "Pond ID is required")
    private Long pondId;

    private LocalDate feedDate;  // defaults to today

    @NotNull(message = "Feed time is required")
    private FeedTime feedTime;

    @NotNull(message = "Feed stage is required")
    private FeedStage feedStage;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Double quantityKg;

    private String brand;
    private String remarks;
}