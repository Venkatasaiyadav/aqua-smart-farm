// src/main/java/com/aquafarm/farm/repository/FarmRepository.java
package com.aquafarm.farm.repository;

import com.aquafarm.farm.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    Optional<Farm> findByOwnerId(Long ownerId);
}
