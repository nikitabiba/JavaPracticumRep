package ru.omstu.impl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omstu.api.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JsonDataProvider implements DataProvider {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Pattern ARRAY_INDEX = Pattern.compile("^\\[(\\d+)]$");

    @Override
    public String getValue(String filePath, String fieldPath) {
        JsonNode root = readRoot(filePath);
        JsonNode current = root;

        String[] parts = fieldPath.replaceFirst("^/", "").split("/");

        for (String part : parts) {
            if (part.isEmpty()) continue;

            Matcher m = ARRAY_INDEX.matcher(part);
            if (m.matches()) {
                int index = Integer.parseInt(m.group(1));
                current = current.get(index);
            } else {
                current = current.get(part);
            }

            if (current == null) {
                throw new IllegalArgumentException("Wrong path.");
            }
        }

        return current.isTextual() ? current.asText() : current.toString();
    }

    private JsonNode readRoot(String filePath) {
        try {
            File file = resolveFile(filePath);
            return MAPPER.readTree(file);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read json: " + filePath, e);
        }
    }

    private File resolveFile(String filePath) {
        File f = new File(filePath);
        if (f.isAbsolute() && f.exists()) return f;
        var url = getClass().getClassLoader().getResource(filePath);
        if (url != null) return new File(url.getFile());
        throw new IllegalArgumentException("File not found: " + filePath);
    }
}
