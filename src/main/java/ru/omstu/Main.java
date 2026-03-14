package ru.omstu;

import ru.omstu.api.DataProvider;
import ru.omstu.factory.DataProviderFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            runDemo();
            return;
        }

        String filePath  = args[0];
        String fieldPath = args[1];

        DataProvider provider = DataProviderFactory.createByFilename(filePath);
        String result = provider.getValue(filePath, fieldPath);
        System.out.println(result);
    }

    private static void runDemo() {
        System.out.println("=== Демо: JSON ===");
        DataProvider json = DataProviderFactory.createByFilename("person.json");

        printResult(json, "person.json", "/name");
        printResult(json, "person.json", "/email");
        printResult(json, "person.json", "/skills/[0]");
        printResult(json, "person.json", "/relation/[0]/name");
        printResult(json, "person.json", "/relation/[1]/name");
        printResult(json, "person.json", "/relation/[1]/status");

        System.out.println();
        System.out.println("=== Демо: XML ===");
        DataProvider xml = DataProviderFactory.createByFilename("person.xml");

        printResult(xml, "person.xml", "/name");
        printResult(xml, "person.xml", "/email");
        printResult(xml, "person.xml", "/skills/[0]");
        printResult(xml, "person.xml", "/relation/[0]/name");
        printResult(xml, "person.xml", "/relation/[1]/name");
        printResult(xml, "person.xml", "/relation/[1]/status");
    }

    private static void printResult(DataProvider provider, String file, String path) {
        try {
            String val = provider.getValue(file, path);
            System.out.printf("%-35s -> %s%n", path, val);
        } catch (Exception e) {
            System.out.printf("%-35s -> Error: %s%n", path, e.getMessage());
        }
    }
}