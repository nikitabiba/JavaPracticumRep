package fitprogwork.impl;

import fitprogwork.service.impl.XmlDataExtractorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlDataExtractorServiceTest {

    private final XmlDataExtractorService service = new XmlDataExtractorService();

    @Test
    void simpleField() {
        assertEquals("Alex", service.extract("<root><name>Alex</name></root>", "name"));
    }

    @Test
    void nestedField() {
        assertEquals("Alex", service.extract("<root><user><name>Alex</name></user></root>", "user/name"));
    }

    @Test
    void arrayElement() {
        assertEquals("Java", service.extract(
                "<root><skills><skill>Java</skill><skill>SQL</skill></skills></root>",
                "skills/[0]"));
    }

    @Test
    void wrongPath_throwsException() {
        assertThrows(RuntimeException.class,
                () -> service.extract("<root><name>Alex</name></root>", "nonexistent"));
    }
}