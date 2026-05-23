package fitprogwork.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Primary
public class InMemoryCacheService implements CacheService {

    private static final Logger log = LoggerFactory.getLogger(InMemoryCacheService.class);
    private final Map<String, String> cache = new HashMap<>();

    @Override
    public String get(String key) {
        if (cache.containsKey(key)) {
            log.info("Cache HIT for key: {}", key);
            return cache.get(key);
        }
        log.info("Cache MISS for key: {}", key);
        return null;
    }

    @Override
    public void put(String key, String value) {
        log.info("Cache PUT for key: {}", key);
        cache.put(key, value);
    }

    @Override
    public String buildKey(String type, String data, String path) {
        return type + "::" + data + "::" + path;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public void deleteOlderThan(int seconds) {
        log.info("InMemory: deleteOlderThan not tracked by time, skipping");
    }

    @Override
    public void deleteAll() {
        log.info("InMemory: clearing all cache entries");
        cache.clear();
    }
}