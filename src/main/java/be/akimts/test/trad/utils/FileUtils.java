package be.akimts.test.trad.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

public class FileUtils {

    public static final String CONFIG_FILE_NAME = "config.keyvalue";
    public static final String TRADFILE_NAME_FORMAT ="trad/%s.keyvalue";
    private static final String SAVEFILE_PATH = "csv/products.csv";

    public static String getResourcePath(String resourceName){
        return Thread.currentThread()
                .getContextClassLoader()
                .getResource(resourceName)
                .getFile();
    }

    public static Map<String, String> getConfig() throws IOException {
        return new KeyValueReader(new File(getResourcePath(CONFIG_FILE_NAME))).toMap();
    }

    public static Map<String, String> getTrad(String tradcode) throws IOException {
        String tradFile = getResourcePath(
                String.format(
                        TRADFILE_NAME_FORMAT,
                        tradcode == null ? "fr" : tradcode
                )
        );
        return new KeyValueReader(new File(tradFile)).toMap();
    }

    public static File getFile(String filename) throws IOException {

        String[] pathParts = filename.split("/");

        String[] folders = Arrays.stream(pathParts)
                .limit(pathParts.length-1)
                .toArray(String[]::new);

        String folderName = "";
        Path folderPath;

        for (String folder : folders) {
            folderName += folder;
            folderPath = Paths.get(folderName);

            if(Files.notExists(folderPath)){
                System.out.println("Creating folder:" + folderPath.toAbsolutePath());
                Files.createDirectory(folderPath);
            }
        }

        Path filePath = Paths.get(filename);

        if(Files.notExists(filePath)){
            System.out.println("Creating file:" + filePath.toAbsolutePath());
            Files.createFile(filePath);
        }

        return new File(filePath.toUri());
    }
}
