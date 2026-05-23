package fitprogwork.impl;

import fitprogwork.service.impl.YamlDataExtractorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class YamlDataExtractorServiceTest {

    private final YamlDataExtractorService service = new YamlDataExtractorService();

    @Test
    void simpleField() {
        assertEquals("Alex", service.extract("name: Alex", "name"));
    }

    @Test
    void nestedField() {
        assertEquals("Alex", service.extract("user:\n  name: Alex", "user/name"));
    }

    @Test
    void arrayElement() {
        assertEquals("Java", service.extract("skills:\n  - Java\n  - SQL", "skills/[0]"));
    }

    @Test
    void wrongPath_throwsException() {
        assertThrows(RuntimeException.class,
                () -> service.extract("name: Alex", "nonexistent"));
    }
}