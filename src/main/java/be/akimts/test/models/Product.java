package be.akimts.test.models;

import java.util.HashMap;
import java.util.Map;

public record Product(
        Long id,
        String model,
        String brand,
        double price
) {
    public String toCSVLine(){
        return id()+","+model()+","+brand()+","+price();
    }
    public String toCSVLine(String header, String delimiter){
        String[] keys = header.split(delimiter);
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            indexMap.put( keys[i].trim(), i );
        }

        String[] values = new String[4];
        values[indexMap.get("id")] = id().toString();
        values[indexMap.get("model")] = model();
        values[indexMap.get("brand")] = brand();
        values[indexMap.get("price")] = Double.toString(price());
        return String.join(delimiter, values);
    }

    public static Product fromCSVLine(String line, String header, String delimiter) {
        String[] keys = header.split(delimiter);
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            indexMap.put( keys[i].trim(), i );
        }
        String[] values = line.split(delimiter);

        long id = Long.parseLong( values[indexMap.get("id")] );
        String model = values[indexMap.get("model")];
        String brand = values[indexMap.get("brand")];
        double price= Double.parseDouble(values[indexMap.get("price")]);

        return new Product(id, model, brand,price);
    }

    public static Product fromCSVLine(String line, String header) {
        return fromCSVLine(line, header, ",");
    }
}
