// src/main/java/com/aquafarm/pond/entity/Pond.java
package com.aquafarm.pond.entity;

import com.aquafarm.common.enums.PondStatus;
import com.aquafarm.farm.entity.Farm;
import com.aquafarm.feed.entity.FeedEntry;
import com.aquafarm.growth.entity.GrowthSample;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ponds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pond_name", nullable = false, length = 50)
    private String pondName;

    @Column(name = "size_acre", nullable = false)
    private Double sizeAcre;

    @Column(name = "stocking_date")
    private LocalDate stockingDate;

    @Column(name = "seed_count")
    @Builder.Default
    private Integer seedCount = 0;

    @Column(name = "prawn_type", length = 50)
    @Builder.Default
    private String prawnType = "VANNAMEI";

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private PondStatus status = PondStatus.ACTIVE;

    /*
     📚 LEARN: @ManyToOne
     Many Ponds belong to One Farm
     This is the "child" side of the relationship
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "pond", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    @Builder.Default
    private List<FeedEntry> feedEntries = new ArrayList<>();

    @OneToMany(mappedBy = "pond", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    @Builder.Default
    private List<GrowthSample> growthSamples = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
     📚 LEARN: @Transient
     This field is NOT stored in database.
     It's calculated on the fly.
    */
    @Transient
    public long getCultureDay() {
        if (stockingDate == null) return 0;
        return ChronoUnit.DAYS.between(stockingDate, LocalDate.now());
    }

    @Transient
    public String getGrowthStage() {
        long day = getCultureDay();
        if (day <= 30) return "NURSERY";
        if (day <= 60) return "JUVENILE";
        if (day <= 90) return "GROW_OUT";
        return "HARVEST_READY";
    }
}