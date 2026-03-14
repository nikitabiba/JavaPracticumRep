package ru.omstu.factory;
import ru.omstu.api.DataProvider;
import ru.omstu.impl.JsonDataProvider;
import ru.omstu.impl.XmlDataProvider;


public class DataProviderFactory {
    private DataProviderFactory() {}

    public static DataProvider create(FileType type) {
        return switch (type) {
            case JSON -> new JsonDataProvider();
            case XML  -> new XmlDataProvider();
        };
    }

    public static DataProvider createByFilename(String filename) {
        return create(FileType.fromFilename(filename));
    }
}
