package fitprogwork.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataProcessingService {

    private final Map<String, DataExtractorService> extractors;

    public DataProcessingService(Map<String, DataExtractorService> extractors) {
        this.extractors = extractors;
    }

    public String extract(String type, String data, String path) {
        DataExtractorService extractor = extractors.get(type);
        if (extractor == null) throw new IllegalArgumentException("Unknown type: " + type);
        return extractor.extract(data, path);
    }
}