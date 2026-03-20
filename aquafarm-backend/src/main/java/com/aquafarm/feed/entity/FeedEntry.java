// src/main/java/com/aquafarm/feed/entity/FeedEntry.java
package com.aquafarm.feed.entity;

import com.aquafarm.common.enums.FeedStage;
import com.aquafarm.common.enums.FeedTime;
import com.aquafarm.pond.entity.Pond;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "feed_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_date", nullable = false)
    private LocalDate feedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "feed_time", nullable = false, length = 20)
    private FeedTime feedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "feed_stage", nullable = false, length = 20)
    private FeedStage feedStage;

    @Column(name = "quantity_kg", nullable = false)
    private Double quantityKg;

    @Column(length = 50)
    @Builder.Default
    private String brand = "AVANTI";

    @Column(length = 255)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pond_id", nullable = false)
    private Pond pond;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}