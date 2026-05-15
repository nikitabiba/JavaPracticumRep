package fitprogwork.service.impl;

import fitprogwork.service.DataExtractorService;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("xml")
public class XmlDataExtractorService implements DataExtractorService {

    private static final Pattern ARRAY_INDEX = Pattern.compile("^\\[(\\d+)]$");

    @Override
    public String extract(String data, String path) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
            Node current = doc.getDocumentElement();

            for (String part : path.replaceFirst("^/", "").split("/")) {
                if (part.isEmpty()) continue;
                Matcher m = ARRAY_INDEX.matcher(part);
                if (m.matches()) {
                    List<Node> children = elementChildren(current);
                    int index = Integer.parseInt(m.group(1));
                    if (index >= children.size()) throw new IllegalArgumentException("Index out of range: " + index);
                    current = children.get(index);
                } else {
                    current = findChild(current, part);
                    if (current == null) throw new IllegalArgumentException("Element not found: " + part);
                }
            }
            return current.getTextContent().trim();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Node findChild(Node parent, String tag) {
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(tag)) return n;
        }
        return null;
    }

    private List<Node> elementChildren(Node parent) {
        List<Node> result = new ArrayList<>();
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) result.add(n);
        }
        return result;
    }
}