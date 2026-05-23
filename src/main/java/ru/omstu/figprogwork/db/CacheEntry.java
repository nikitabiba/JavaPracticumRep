package ru.omstu.figprogwork.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "cache_entry")
public class CacheEntry {

    @Id
    @Column(name = "cache_key", length = 2000)
    private String cacheKey;

    @Column(name = "cache_value", length = 5000)
    private String cacheValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CacheEntry() {}

    public CacheEntry(String cacheKey, String cacheValue, LocalDateTime createdAt) {
        this.cacheKey = cacheKey;
        this.cacheValue = cacheValue;
        this.createdAt = createdAt;
    }

    public String getCacheKey() { return cacheKey; }
    public String getCacheValue() { return cacheValue; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}