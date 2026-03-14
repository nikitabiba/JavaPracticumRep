package ru.omstu.factory;

public enum FileType {
    JSON,
    XML;

    public static FileType fromFilename(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".json")) return JSON;
        if (lower.endsWith(".xml"))  return XML;
        throw new IllegalArgumentException("Wrong filetype: " + filename);
    }
}
