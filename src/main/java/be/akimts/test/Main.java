package be.akimts.test;

import be.akimts.test.models.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String folderName = "csv";
        String fileName = "products.csv";

        Path folderPath = Paths.get("csv");
        Path filePath = Paths.get(folderName, fileName);

        if(Files.notExists(folderPath)){
            System.out.println("Creating folder:" + folderPath.toAbsolutePath());
            Files.createDirectory(folderPath);
        }

        if(Files.notExists(filePath)){
            System.out.println("Creating file:" + filePath.toAbsolutePath());
            Files.createFile(filePath);
        }

        File file = new File(filePath.toUri());

        try(
                InputStream in = new FileInputStream(file);
        ){
            String content = new String(in.readAllBytes());
            System.out.println(content);

            try(
                    OutputStream out = new FileOutputStream(file)
            ){
                if(content.trim().isEmpty()){
                    content += "id,model,brand,price";
                }

                Product product = new Product(11L, "machin", "truc", 10.1);

                content+= '\n'+product.toCSVLine();

                out.write(content.getBytes());
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
