package be.akimts.test.trad.service;

import be.akimts.test.models.Product;

import java.util.Set;

public interface ProductService {
    Set<Product> getAll();
    Product add(String model, String brand, double price);
    Product remove(long id);
    void save() throws SaveException;
}
