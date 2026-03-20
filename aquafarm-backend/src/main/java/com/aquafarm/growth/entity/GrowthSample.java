// src/main/java/com/aquafarm/growth/entity/GrowthSample.java
package com.aquafarm.growth.entity;

import com.aquafarm.pond.entity.Pond;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "growth_samples")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowthSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sample_date", nullable = false)
    private LocalDate sampleDate;

    @Column(name = "avg_weight_grams", nullable = false)
    private Double avgWeightGrams;

    @Column(name = "sample_count")
    @Builder.Default
    private Integer sampleCount = 50;

    @Column(name = "survival_rate")
    private Double survivalRate;

    /*
     📚 LEARN: Biomass Calculation
     Biomass = Number of alive prawns × Average weight
     
     alive prawns = seedCount × (survivalRate / 100)
     biomass = alive × avgWeight / 1000 (convert grams to kg)
     
     Example:
     Seeds: 200,000
     Survival: 85%
     Avg Weight: 12g
     
     Alive = 200,000 × 0.85 = 170,000
     Biomass = 170,000 × 12 / 1000 = 2,040 kg
    */
    @Column(name = "estimated_biomass")
    private Double estimatedBiomass;

    @Column(name = "health_status", length = 20)
    @Builder.Default
    private String healthStatus = "HEALTHY";

    @Column(length = 255)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pond_id", nullable = false)
    private Pond pond;

    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}