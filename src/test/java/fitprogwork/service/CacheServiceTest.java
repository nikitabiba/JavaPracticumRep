package fitprogwork.service;

import fitprogwork.service.InMemoryCacheService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class CacheServiceTest {

    @Autowired
    private DataProcessingService processingService;

    @Autowired
    private InMemoryCacheService cacheService;;

    @Test
    void firstCall_missesCache_secondCall_hitsCache() {
        String type = "json";
        String data = "{ \"test\": \"cache\" }";
        String path = "test";
        String key = cacheService.buildKey(type, data, path);

        assertNull(cacheService.get(key));

        processingService.extract(type, data, path);
        assertEquals("cache", cacheService.get(key));

        processingService.extract(type, data, path);
        assertEquals("cache", cacheService.get(key));
    }

    @Test
    void differentRequests_cachedSeparately() {
        processingService.extract("json", "{ \"a\": \"1\" }", "a");
        processingService.extract("json", "{ \"b\": \"2\" }", "b");

        assertEquals("1", cacheService.get(cacheService.buildKey("json", "{ \"a\": \"1\" }", "a")));
        assertEquals("2", cacheService.get(cacheService.buildKey("json", "{ \"b\": \"2\" }", "b")));
    }
}