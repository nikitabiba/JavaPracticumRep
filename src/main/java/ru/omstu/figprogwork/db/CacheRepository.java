package ru.omstu.figprogwork.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CacheRepository extends JpaRepository<CacheEntry, String> {

    @Modifying
    @Query("DELETE FROM CacheEntry c WHERE c.createdAt < :threshold")
    void deleteOlderThan(@Param("threshold") LocalDateTime threshold);
}