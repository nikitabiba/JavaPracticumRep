package fitprogwork.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {

    private static final Logger log = LoggerFactory.getLogger(CacheService.class);
    private final Map<String, String> cache = new HashMap<>();

    public String get(String key) {
        if (cache.containsKey(key)) {
            log.info("Cache HIT for key: {}", key);
            return cache.get(key);
        }
        log.info("Cache MISS for key: {}", key);
        return null;
    }

    public void put(String key, String value) {
        log.info("Cache PUT for key: {}", key);
        cache.put(key, value);
    }

    public String buildKey(String type, String data, String path) {
        return type + "::" + data + "::" + path;
    }

    public int size() {
        return cache.size();
    }
}