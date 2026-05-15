package fitprogwork.impl;

import fitprogwork.service.impl.JsonDataExtractorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonDataExtractorServiceTest {

    private final JsonDataExtractorService service = new JsonDataExtractorService();

    @Test
    void simpleField() {
        assertEquals("Alex", service.extract("{ \"name\": \"Alex\" }", "name"));
    }

    @Test
    void nestedField() {
        assertEquals("Alex", service.extract("{ \"user\": { \"name\": \"Alex\" } }", "user/name"));
    }

    @Test
    void arrayElement() {
        assertEquals("Java", service.extract("{ \"skills\": [\"Java\", \"SQL\"] }", "skills/[0]"));
    }

    @Test
    void wrongPath_throwsException() {
        assertThrows(RuntimeException.class,
                () -> service.extract("{ \"name\": \"Alex\" }", "nonexistent"));
    }
}