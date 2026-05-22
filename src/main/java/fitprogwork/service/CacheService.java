package fitprogwork.service;

public interface CacheService {
    String get(String key);
    void put(String key, String value);
    String buildKey(String type, String data, String path);
    int size();
    void deleteOlderThan(int seconds);
    void deleteAll();
}