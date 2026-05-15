package fitprogwork.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DataProcessingServiceTest {

    @Autowired
    private DataProcessingService service;

    @Test
    void jsonExtractor_isSelected() {
        String result = service.extract("json", "{ \"key\": \"value\" }", "key");
        assertEquals("value", result);
    }

    @Test
    void xmlExtractor_isSelected() {
        String result = service.extract("xml", "<root><key>value</key></root>", "key");
        assertEquals("value", result);
    }

    @Test
    void yamlExtractor_isSelected() {
        String result = service.extract("yaml", "key: value", "key");
        assertEquals("value", result);
    }

    @Test
    void unknownType_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.extract("csv", "a,b", "a"));
    }
}