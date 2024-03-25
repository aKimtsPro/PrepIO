package be.akimts.test.trad.utils;

import java.io.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class KeyValueReader extends BufferedReader {
    public KeyValueReader(File file) throws FileNotFoundException {
        super(new FileReader(file));
    }

    public Map<String, String> toMap() throws IOException {
        try {
            return this.lines()
                    .map(KeyValueReader::parseLine)
                    .collect(
                            HashMap::new,
                            (map, item) -> map.put(item.getKey(), item.getValue()),
                            HashMap::putAll
                    );
        } catch (Exception ex){
            throw new RuntimeException("not parsable");
        }
        finally {
            this.close();
        }
    }

    private static Map.Entry<String,String> parseLine(String line){
        String[] parts = line.split(":");
        if( parts.length != 2 )
            throw new RuntimeException("Line not parsable");

        return new AbstractMap.SimpleEntry<>(parts[0], parts[1]);
    }
}
