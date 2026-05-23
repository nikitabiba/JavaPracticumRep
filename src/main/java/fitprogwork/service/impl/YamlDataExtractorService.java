package fitprogwork.service.impl;

import fitprogwork.service.DataExtractorService;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("yaml")
public class YamlDataExtractorService implements DataExtractorService {

    private static final Pattern ARRAY_INDEX = Pattern.compile("^\\[(\\d+)]$");

    @Override
    public String extract(String data, String path) {
        Yaml yaml = new Yaml();
        Object root = yaml.load(data);

        Object current = root;
        for (String part : path.replaceFirst("^/", "").split("/")) {
            if (part.isEmpty()) continue;
            Matcher m = ARRAY_INDEX.matcher(part);
            if (m.matches()) {
                int index = Integer.parseInt(m.group(1));
                if (!(current instanceof List)) throw new IllegalArgumentException("Not a list at: " + part);
                current = ((List<?>) current).get(index);
            } else {
                if (!(current instanceof Map)) throw new IllegalArgumentException("Not an object at: " + part);
                current = ((Map<?, ?>) current).get(part);
                if (current == null) throw new IllegalArgumentException("Key not found: " + part);
            }
        }
        return String.valueOf(current);
    }
}