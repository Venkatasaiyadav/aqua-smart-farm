// src/main/java/com/aquafarm/pond/repository/PondRepository.java
package com.aquafarm.pond.repository;

import com.aquafarm.pond.entity.Pond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PondRepository extends JpaRepository<Pond, Long> {
    List<Pond> findByFarmId(Long farmId);
    List<Pond> findByFarmIdAndStatus(Long farmId, 
                com.aquafarm.common.enums.PondStatus status);
}