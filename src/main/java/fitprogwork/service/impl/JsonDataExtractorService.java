package fitprogwork.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fitprogwork.service.DataExtractorService;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("json")
public class JsonDataExtractorService implements DataExtractorService {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Pattern ARRAY_INDEX = Pattern.compile("^\\[(\\d+)]$");

    @Override
    public String extract(String data, String path) {
        try {
            JsonNode current = MAPPER.readTree(data);
            for (String part : path.replaceFirst("^/", "").split("/")) {
                if (part.isEmpty()) continue;
                Matcher m = ARRAY_INDEX.matcher(part);
                if (m.matches()) {
                    current = current.get(Integer.parseInt(m.group(1)));
                } else {
                    current = current.get(part);
                }
                if (current == null) throw new IllegalArgumentException("Path not found: " + part);
            }
            return current.isTextual() ? current.asText() : current.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}