package be.akimts.test.trad.service;

import be.akimts.test.models.Product;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private final File saveFile;
    private final Set<Product> products;
    private long nextId;

    public ProductServiceImpl(File saveFile) {
        this.saveFile = saveFile;
        this.products = Optional
                .ofNullable(this.load())
                .orElseGet(HashSet::new);
        this.nextId = products.stream()
                .mapToLong(Product::id)
                .max()
                .orElse(0L) + 1;
    }

    public Set<Product> getAll(){
        return new HashSet<>(products);
    }

    public Product add(
            String model,
            String brand,
            double price
    ) {
        Product toAdd = new Product(nextId++, model, brand, price);
        this.products.add(toAdd);
        return toAdd;
    }

    public Product remove(long id) {
        Product toRemove = products.stream()
                .filter(p -> p.id() == id)
                .findFirst()
                .orElseThrow();
        products.remove(toRemove);
        return toRemove;
    }

    public void save() throws SaveException {
        String header = "id,model,brand,price";
        try (BufferedWriter out = new BufferedWriter(new FileWriter(saveFile))) {
            out.write(header+'\n');
            for (Product product : products) {
                out.write(product.toCSVLine(header, ","));
                out.newLine();
            }
        } catch (IOException cause) {
            throw new SaveException(saveFile, cause);
        }
    }

    private Set<Product> load(){
        try( BufferedReader in = new BufferedReader(new FileReader(saveFile)) ){
            String header = in.lines()
                    .limit(1)
                    .findFirst()
                    .orElseThrow();

            return in.lines()
                    .map(line -> Product.fromCSVLine(line, header))
                    .collect(Collectors.toCollection(HashSet::new));
        } catch (Exception exception) {
            return null;
        }
    }
}
