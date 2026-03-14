package ru.omstu.api;

public interface DataProvider {
    String getValue(String filePath, String fieldPath);
}