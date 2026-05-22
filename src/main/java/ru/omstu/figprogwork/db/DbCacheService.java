package ru.omstu.figprogwork.db;

import fitprogwork.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DbCacheService implements CacheService {

    private static final Logger log = LoggerFactory.getLogger(DbCacheService.class);
    private final CacheRepository repository;

    public DbCacheService(CacheRepository repository) {
        this.repository = repository;
    }

    @Override
    public String get(String key) {
        Optional<CacheEntry> entry = repository.findById(key);
        if (entry.isPresent()) {
            log.info("DB Cache HIT for key: {}", key);
            return entry.get().getCacheValue();
        }
        log.info("DB Cache MISS for key: {}", key);
        return null;
    }

    @Override
    @Transactional
    public void put(String key, String value) {
        log.info("DB Cache PUT for key: {}", key);
        repository.save(new CacheEntry(key, value, LocalDateTime.now()));
    }

    @Override
    public String buildKey(String type, String data, String path) {
        return type + "::" + data + "::" + path;
    }

    @Override
    public int size() {
        return (int) repository.count();
    }

    @Override
    @Transactional
    public void deleteOlderThan(int seconds) {
        LocalDateTime threshold = LocalDateTime.now().minusSeconds(seconds);
        log.info("DB Cache: deleting entries older than {} seconds", seconds);
        repository.deleteOlderThan(threshold);
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.info("DB Cache: deleting all entries");
        repository.deleteAll();
    }
}