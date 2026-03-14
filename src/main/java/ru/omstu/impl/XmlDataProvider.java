package ru.omstu.impl;
import ru.omstu.api.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XmlDataProvider implements DataProvider {

    private static final Pattern ARRAY_INDEX = Pattern.compile("^\\[(\\d+)]$");

    @Override
    public String getValue(String filePath, String fieldPath) {
        Document doc = parseDocument(filePath);

        Node current = doc.getDocumentElement();

        String[] parts = fieldPath.replaceFirst("^/", "").split("/");

        for (String part : parts) {
            if (part.isEmpty()) continue;

            Matcher m = ARRAY_INDEX.matcher(part);
            if (m.matches()) {
                int index = Integer.parseInt(m.group(1));
                List<Node> children = elementChildren(current);
                if (index >= children.size()) {
                    throw new IllegalArgumentException(
                            "Index " + index + " out of range (size: " + children.size() + ")");
                }
                current = children.get(index);
            } else {
                current = findChild(current, part);
                if (current == null) {
                    throw new IllegalArgumentException("Element <" + part + "> not found");
                }
            }
        }

        return current.getTextContent().trim();
    }

    private Node findChild(Node parent, String tag) {
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(tag)) {
                return n;
            }
        }
        return null;
    }

    private List<Node> elementChildren(Node parent) {
        List<Node> result = new ArrayList<>();
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                result.add(n);
            }
        }
        return result;
    }

    private Document parseDocument(String filePath) {
        try {
            File file = resolveFile(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read xml: " + filePath, e);
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
