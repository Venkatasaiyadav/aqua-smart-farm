// src/main/java/com/aquafarm/feed/dto/FeedEntryResponse.java
package com.aquafarm.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedEntryResponse {

    private Long id;
    private Long pondId;
    private String pondName;
    private LocalDate feedDate;
    private String feedTime;
    private String feedStage;
    private Double quantityKg;
    private String brand;
    private String remarks;
    private LocalDateTime createdAt;
}