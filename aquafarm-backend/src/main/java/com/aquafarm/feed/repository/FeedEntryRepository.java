// src/main/java/com/aquafarm/feed/repository/FeedEntryRepository.java
package com.aquafarm.feed.repository;

import com.aquafarm.feed.entity.FeedEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeedEntryRepository extends JpaRepository<FeedEntry, Long> {

    // Find all feed entries for a pond
    List<FeedEntry> findByPondIdOrderByFeedDateDesc(Long pondId);

    // Find feed entries for a pond on specific date
    List<FeedEntry> findByPondIdAndFeedDate(Long pondId, LocalDate date);

    // Find today's feed for all ponds of a farm
    @Query("SELECT f FROM FeedEntry f WHERE f.pond.farm.id = :farmId " +
           "AND f.feedDate = :date ORDER BY f.pond.id")
    List<FeedEntry> findByFarmIdAndDate(
            @Param("farmId") Long farmId,
            @Param("date") LocalDate date);

    // Total feed for a pond
    @Query("SELECT COALESCE(SUM(f.quantityKg), 0) FROM FeedEntry f " +
           "WHERE f.pond.id = :pondId")
    Double getTotalFeedByPondId(@Param("pondId") Long pondId);

    // Total feed for a pond on a date
    @Query("SELECT COALESCE(SUM(f.quantityKg), 0) FROM FeedEntry f " +
           "WHERE f.pond.id = :pondId AND f.feedDate = :date")
    Double getTotalFeedByPondIdAndDate(
            @Param("pondId") Long pondId,
            @Param("date") LocalDate date);

    // Total feed for entire farm
    @Query("SELECT COALESCE(SUM(f.quantityKg), 0) FROM FeedEntry f " +
           "WHERE f.pond.farm.id = :farmId")
    Double getTotalFeedByFarmId(@Param("farmId") Long farmId);
}