// src/main/java/com/aquafarm/growth/repository/GrowthSampleRepository.java
package com.aquafarm.growth.repository;

import com.aquafarm.growth.entity.GrowthSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrowthSampleRepository 
        extends JpaRepository<GrowthSample, Long> {

    List<GrowthSample> findByPondIdOrderBySampleDateDesc(Long pondId);

    // Get latest sample for a pond
    Optional<GrowthSample> findTopByPondIdOrderBySampleDateDesc(
            Long pondId);
}