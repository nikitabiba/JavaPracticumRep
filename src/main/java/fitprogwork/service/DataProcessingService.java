package fitprogwork.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataProcessingService {

    private final Map<String, DataExtractorService> extractors;
    private final InMemoryCacheService cacheService;

    public DataProcessingService(Map<String, DataExtractorService> extractors, InMemoryCacheService cacheService) {
        this.extractors = extractors;
        this.cacheService = cacheService;
    }

    public String extract(String type, String data, String path) {
        String key = cacheService.buildKey(type, data, path);
        String cached = cacheService.get(key);
        if (cached != null) return cached;

        DataExtractorService extractor = extractors.get(type);
        if (extractor == null) throw new IllegalArgumentException("Unknown type: " + type);

        String result = extractor.extract(data, path);
        cacheService.put(key, result);
        return result;
    }
}